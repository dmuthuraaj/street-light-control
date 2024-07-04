package com.opzero.device.config;

import com.opzero.device.service.DeviceService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    @Autowired
    private final DeviceService deviceService;

    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    public void updateInactiveDevices() {
        deviceService.updateDeviceStatus();
    }
}
