package com.opzero.device.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker.url}")
    private String serverUrl;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(serverUrl, MqttClient.generateClientId());
        client.connect();
        return client;
        // try {
            //             if (instance == null) {
            //                 instance = new MqttClient(MQTT_SERVER_ADDRES, MQTT_PUBLISHER_ID);
            //             }
            
            //             MqttConnectOptions options = new MqttConnectOptions();
            //             options.setAutomaticReconnect(true);
            //             options.setCleanSession(true);
            //             options.setConnectionTimeout(10);
            
            //             if (!instance.isConnected()) {
            //                 instance.connect(options);
            //             }
            //         } catch (MqttException e) {
            //             e.printStackTrace();
            //         }
            
            //         return instance;
    }
}
