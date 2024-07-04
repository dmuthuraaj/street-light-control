package com.opzero.device;

import lombok.Data;

@Data
public class PowerDetails {
    private String powerStatus;
    private String voltage;
    private String current;
    private String unit;
}
