package com.opzero.device.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeviceCreateRequest {
    @NotEmpty
    private String macAddress;
}
