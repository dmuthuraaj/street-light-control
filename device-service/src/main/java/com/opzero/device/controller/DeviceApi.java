package com.opzero.device.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opzero.core.dto.CommonResponse;
import com.opzero.device.dto.request.DeviceCreateRequest;
import com.opzero.device.dto.request.DeviceUpdateRequest;
import com.opzero.device.mongo.entity.Device;
import com.opzero.device.service.DeviceService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class DeviceApi {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    ResponseEntity<CommonResponse<List<Device>>> getAllDeviceData() {
        List<Device> list= deviceService.getAll();
        CommonResponse<List<Device>> commonResponse = new CommonResponse<>();
        commonResponse.setCode(200);
        commonResponse.setData(list);
        commonResponse.setMessage("devices listed successfully");
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("/{deviceId}")
    ResponseEntity<CommonResponse<Device>> getDeviceData(@PathVariable(name = "deviceId",required = true) String deviceId) {
        Device device= deviceService.get(deviceId);
        CommonResponse<Device> commonResponse = new CommonResponse<>();
        commonResponse.setCode(200);
        commonResponse.setData(device);
        commonResponse.setMessage("devices listed successfully");
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("/add")
    ResponseEntity<CommonResponse<Boolean>> createDeviceData(@RequestBody DeviceCreateRequest request) {
        boolean isDone = deviceService.add(request);
        CommonResponse<Boolean> commonResponse = new CommonResponse<>();
        commonResponse.setCode(200);
        commonResponse.setData(isDone);
        commonResponse.setMessage("device added successfully");
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("/{deviceId}/settings")
    ResponseEntity<CommonResponse<String>>settings(@PathVariable(name = "deviceId",required = true) String deviceId,
    @RequestBody DeviceUpdateRequest request){
        deviceService.updateDeviceSettings(deviceId,request);
        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setCode(200);
        commonResponse.setData("SUCCESS");
        commonResponse.setMessage("settings updated successfully");
        return ResponseEntity.ok(commonResponse);
    }
}
