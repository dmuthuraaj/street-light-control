package com.opzero.device.dto.request;

import lombok.Data;

@Data
public class Message{
    // private String macAddress;
    // private String powerStatus;
    // private String voltage;
    // private String current;
    // private String unit;

    private String pingTime;
    private String currentTime;
    private String onTime;
    private String offTime;
    private String lightStatus;
    private String lightOnTime;
    private String lightOffTime;
    private String latitude;
    private String longitude;
}
