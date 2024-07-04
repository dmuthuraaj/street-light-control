package com.opzero.device.dto.request;

import lombok.Data;

@Data
public class MqttSubscribeModel {

    private Message message;
    private Integer qos;
    private Integer id;
}