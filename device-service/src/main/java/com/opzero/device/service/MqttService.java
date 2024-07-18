package com.opzero.device.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opzero.device.LightDetails;
import com.opzero.device.LocationDetails;
import com.opzero.device.PowerDetails;
import com.opzero.device.config.MqttManager;
import com.opzero.device.dto.request.Message;
import com.opzero.device.exception.UnableToCreateException;
import com.opzero.device.mapper.DeviceMapper;
import com.opzero.device.mongo.entity.Device;
import com.opzero.device.mongo.repository.DeviceRepository;
import com.opzero.device.util.GetCity;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqttService {

    private final DeviceRepository deviceDataRepository;

    private final MqttManager mqttManager;

    public void publish(String macAddress, String payload) {
        String topic = "iot/devices/settings/" + macAddress;
        MqttMessage message = new MqttMessage();
        message.setPayload(payload.getBytes());
        try {
            mqttManager.getInstance().publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void subscribeToTopics() throws MqttException {
        mqttManager.getInstance().subscribe("iot/devices", new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // todo: Check all exceptions and null values
                String payload = new String(message.getPayload());

                ObjectMapper objectMapper = new ObjectMapper();
                Message decodedMessage = new Message();
                try {
                    decodedMessage = objectMapper.readValue(payload, Message.class);
                } catch (JsonProcessingException e) {
                    throw new UnableToCreateException(e.getMessage());
                }

                Optional<Device> optionalDevice = deviceDataRepository
                        .findOneByMacAddress(decodedMessage.getMacAddress());
                if (optionalDevice.isEmpty()) {
                    log.info("device not found");
                }

                Device device = optionalDevice.get();

                PowerDetails powerDetails = new PowerDetails();
                // powerDetails.setPowerStatus(decodedMessage.getPowerStatus());
                powerDetails.setUnit(decodedMessage.getUnit());
                powerDetails.setCurrent(decodedMessage.getCurrent());
                powerDetails.setVoltage(decodedMessage.getVoltage());
                device.setPowerDetails(powerDetails);

                log.info("Message received: {}", decodedMessage);
                device.setPingTime(decodedMessage.getPingTime());

                LightDetails lightDetails = new LightDetails();
                lightDetails.setLightStatus(decodedMessage.getLightStatus());
                lightDetails.setLightOnTime(decodedMessage.getLightOnTime());
                lightDetails.setLightOffTime(decodedMessage.getLightOffTime());
                device.setLightDetails(lightDetails);

                LocationDetails locationDetails = new LocationDetails();
                locationDetails.setLatitude(decodedMessage.getLatitude());
                locationDetails.setLongitude(decodedMessage.getLongitude());

                ObjectMapper mapper = new ObjectMapper();
                String response = GetCity.reverseGeocode(decodedMessage.getLatitude(), decodedMessage.getLongitude());
                log.info("String response :{}", response);
                JsonNode responseJsonNode = mapper.readTree(response);
                log.info("json Response :{}", responseJsonNode);
                JsonNode address = responseJsonNode.get("address");
                log.info("address: {}", address);

                locationDetails.setCity(address.get("city").asText());

                device.setLocationDetails(locationDetails);

                device.setStatus(DeviceMapper.processOnline());
                device.setHeartbeat(LocalDateTime.now());
                device.setActive(true);
                deviceDataRepository.save(device);
            }
        });
    }
}
