package com.opzero.device.dto.request;

import lombok.Data;

@Data
public class DeviceUpdateRequest {
    private String name;
    private String pingTime;
    // private String onTime;
    // private String offTime;
    private String lightStatus;
    private String lightOnTime;
    private String lightOffTime;
}
