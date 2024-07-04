package com.opzero.device.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opzero.device.LightDetails;
import com.opzero.device.dto.request.DeviceCreateRequest;
import com.opzero.device.dto.request.DeviceUpdateRequest;
import com.opzero.device.dto.request.Message;
import com.opzero.device.exception.DeviceNotFoundException;
import com.opzero.device.exception.EmptyMessageException;
import com.opzero.device.mongo.entity.Device;
import com.opzero.device.mongo.repository.DeviceRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    private final MqttService mqttService;

    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    public Device get(String deviceId) {
        Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
        if (!optionalDevice.isPresent()) {
            throw new DeviceNotFoundException("device not found with deviceId: " + deviceId);
        }
        Device device = optionalDevice.get();
        return device;
    }

    public boolean add(DeviceCreateRequest request) {
        Optional<Device> optionalDevice = deviceRepository.findOneByMacAddress(request.getMacAddress());
        if (optionalDevice.isPresent()) {
            throw new DeviceNotFoundException("device already found with mac address: " + request.getMacAddress());
        }
        Device device = new Device();
        device.setMacAddress(request.getMacAddress());
        device.setHeartbeat(LocalDateTime.now());
        device.setPingTime(null);
        device.setOnTime(null);
        device.setOffTime(null);
        device.setLightDetails(null);
        device.setPowerDetails(null);
        device.setLocationDetails(null);
        device.setStatus("offline");
        device.setActive(false);
        device.setDeleted(false);
        deviceRepository.save(device);
        return true;
    }

    public boolean updateDeviceSettings(String deviceId, DeviceUpdateRequest request) {
        Optional<Device> optionalDevice = deviceRepository.findOneByMacAddress(deviceId);
        if (optionalDevice.isEmpty()) {
            throw new DeviceNotFoundException(deviceId);
        }

        Message message = new Message();
        message.setLightStatus(request.getLightStatus());
        message.setLightOnTime(request.getLightOnTime());
        message.setLightOffTime(request.getLightOffTime());
        message.setPingTime(request.getPingTime());
        message.setOnTime(request.getOnTime());
        message.setOffTime(request.getOffTime());

        // TODO: Make string
        String strMsg = toJson(message);

        mqttService.publish(deviceId, strMsg);

        Device device = optionalDevice.get();
        device.setHeartbeat(LocalDateTime.now());
        device.setStatus("online");
        // log.info("time:
        // {}",LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        LightDetails lightDetails = new LightDetails();
        lightDetails.setLightStatus(request.getLightStatus());
        lightDetails.setLightOnTime(request.getLightOnTime());
        lightDetails.setLightOffTime(request.getLightOffTime());
        device.setLightDetails(lightDetails);

        device.setPingTime(request.getPingTime());
        device.setOnTime(request.getOnTime());
        device.setOffTime(request.getOffTime());

        deviceRepository.save(device);

        return true;
    }

    public void updateDeviceStatus() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);
        List<Device> inactiveDevices = deviceRepository.findAll()
                .stream()
                .filter(Device -> Device.getHeartbeat().isBefore(threshold))
                .collect(Collectors.toList());
        log.info("Updating inactive devices");
        for (Device device : inactiveDevices) {
            device.setStatus("offline");
            deviceRepository.save(device);
        }
    }

    private String toJson(Message message) {
        String strMsg = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            strMsg = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (strMsg == "") {
            throw new EmptyMessageException("message is empty");
        }
        return strMsg;
    }
}
