package com.opzero.device.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MqttPublishModel {

    @NotNull
    @Size(min = 1,max = 255)
    private String topic;

    @NotNull
    private Message message;

    @NotNull
    private Boolean retained;

    @NotNull
    private Integer qos;

}
