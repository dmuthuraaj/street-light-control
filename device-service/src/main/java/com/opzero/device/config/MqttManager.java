package com.opzero.device.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MqttManager {

    private MqttClient mqttClient;

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    public MqttClient getInstance() throws MqttException {
        if (mqttClient == null) {
            mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId());
        }

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setKeepAliveInterval(60);
        options.setConnectionTimeout(60);
        // options.setUserName(username);
        // options.setPassword(password.toCharArray());

        if (!mqttClient.isConnected()) {
            mqttClient.connect(options);
        }
        return mqttClient;
    }
}
