package com.opzero.device.dto.response;

import java.util.Map;

import lombok.Data;

@Data
public class DeviceSummaryResponse {
    private String id;

    private String name;

    private boolean isActive;

    private String model;

    private Map<String, String> tags;
}
