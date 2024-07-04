package com.opzero.device.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opzero.device.LightDetails;
import com.opzero.device.LocationDetails;
import com.opzero.device.dto.request.Message;
import com.opzero.device.exception.UnableToCreateException;
import com.opzero.device.mongo.entity.Device;
import com.opzero.device.mongo.repository.DeviceRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MqttService {

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private DeviceRepository deviceDataRepository;

    public void publish(String deviceId, String payload) {
        String topic = "iot/devices/settings";
        MqttMessage message = new MqttMessage();
        message.setPayload(payload.getBytes());
        try {
            mqttClient.publish(topic , message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void subscribeToTopics() throws MqttException {
        mqttClient.subscribe("iot/devices/#", new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String payload = new String(message.getPayload());
                String mac = topic.split("/")[2];
                Optional<Device> optionalDevice=deviceDataRepository.findOneByMacAddress(mac);
                if (optionalDevice.isEmpty()){
                    log.info("device not found");
                }
                Device device = optionalDevice.get();
                // TODO: get message data and deserialize -> update device
                ObjectMapper objectMapper= new ObjectMapper();
                Message decodedMessage = new Message();
                try{
                    decodedMessage = objectMapper.readValue(payload, Message.class);
                }catch(JsonProcessingException e){
                    throw new UnableToCreateException(e.getMessage());
                }

                log.info("Message received: {}",decodedMessage);
                device.setPingTime(decodedMessage.getPingTime());

                LightDetails lightDetails = new LightDetails();
                lightDetails.setLightStatus(decodedMessage.getLightStatus());
                lightDetails.setLightOnTime(decodedMessage.getLightOnTime());
                lightDetails.setLightOffTime(decodedMessage.getLightOffTime());
                device.setLightDetails(lightDetails);

                LocationDetails locationDetails = new LocationDetails();
                locationDetails.setLatitude(decodedMessage.getLatitude());
                locationDetails.setLongitude(decodedMessage.getLongitude());
                // TODO: update city using lat & long
                // locationDetails.setCity(null);
                device.setLocationDetails(locationDetails);

                device.setStatus("online");
                device.setHeartbeat(LocalDateTime.now());
                device.setActive(true);
                deviceDataRepository.save(device);
            }
        });
    }
}
