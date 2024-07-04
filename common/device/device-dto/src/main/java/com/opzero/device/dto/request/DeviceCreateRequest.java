package com.opzero.device.dto.request;

import java.util.Map;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeviceCreateRequest {
    @NotEmpty
    private String macAddress;
}
