package com.opzero.device.mongo.entity;

import com.opzero.core.mongo.BaseEntity;
import com.opzero.device.LightDetails;
import com.opzero.device.LocationDetails;
import com.opzero.device.PowerDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "devices")
@EqualsAndHashCode(callSuper = true)
public class Device extends BaseEntity {
    private String macAddress;
    private String status;
    private LocalDateTime heartbeat;
    private String pingTime;
    // private String onTime;
    // private String offTime;
    // private String currentTime;
    private PowerDetails powerDetails;
    private LightDetails lightDetails;
    private LocationDetails locationDetails;
}
