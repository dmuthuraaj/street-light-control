// package com.opzero.device.controller;

// import org.apache.commons.lang3.SerializationUtils;
// import org.eclipse.paho.client.mqttv3.MqttMessage;
// import org.springframework.validation.BindingResult;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.*;

// import com.opzero.device.dto.request.Message;
// import com.opzero.device.dto.request.MqttPublishModel;
// import com.opzero.device.dto.request.MqttSubscribeModel;
// import com.opzero.device.exception.ExceptionMessages;
// import com.opzero.device.exception.MqttException;
// import com.opzero.device.service.MqttService;

// import lombok.AllArgsConstructor;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.concurrent.CountDownLatch;
// import java.util.concurrent.TimeUnit;

// @RestController
// @RequestMapping("/broker")
// @AllArgsConstructor
// public class MqttApi {

// @PostMapping("/publish")
// public void publishMessage(@RequestBody @Validated MqttPublishModel
// messagePublishModel,
// BindingResult bindingResult) throws
// org.eclipse.paho.client.mqttv3.MqttException {
// if (bindingResult.hasErrors()) {
// throw new MqttException(ExceptionMessages.SOME_PARAMETERS_INVALID);
// }
// Message message = messagePublishModel.getMessage();
// MqttMessage mqttMessage = new
// MqttMessage(org.springframework.util.SerializationUtils.serialize(message));
// mqttMessage.setQos(messagePublishModel.getQos());
// mqttMessage.setRetained(messagePublishModel.getRetained());

// MqttService.getInstance().publish(messagePublishModel.getTopic(),
// mqttMessage);
// }

// @GetMapping("/subscribe")
// public List<MqttSubscribeModel> subscribeChannel(@RequestParam(value =
// "topic") String topic,
// @RequestParam(value = "wait_millis") Integer waitMillis)
// throws InterruptedException, org.eclipse.paho.client.mqttv3.MqttException {
// List<MqttSubscribeModel> messages = new ArrayList<>();
// CountDownLatch countDownLatch = new CountDownLatch(10);
// MqttService.getInstance().subscribeWithResponse(topic, (s, mqttMessage) -> {
// MqttSubscribeModel mqttSubscribeModel = new MqttSubscribeModel();
// mqttSubscribeModel.setId(mqttMessage.getId());
// Message message =
// org.springframework.util.SerializationUtils.deserialize(mqttMessage.getPayload());
// mqttSubscribeModel.setMessage(message);
// mqttSubscribeModel.setQos(mqttMessage.getQos());
// messages.add(mqttSubscribeModel);
// countDownLatch.countDown();
// });

// countDownLatch.await(waitMillis, TimeUnit.MILLISECONDS);

// return messages;
// }

// }

// private static final String MQTT_PUBLISHER_ID = "spring-server";
// private static final String MQTT_SERVER_ADDRES=
// "tcp://test.mosquitto.org:1883";
// private static IMqttClient instance;

// public static IMqttClient getInstance() {
// try {
// if (instance == null) {
// instance = new MqttClient(MQTT_SERVER_ADDRES, MQTT_PUBLISHER_ID);
// }

// MqttConnectOptions options = new MqttConnectOptions();
// options.setAutomaticReconnect(true);
// options.setCleanSession(true);
// options.setConnectionTimeout(10);

// if (!instance.isConnected()) {
// instance.connect(options);
// }
// } catch (MqttException e) {
// e.printStackTrace();
// }

// return instance;
// }

// private MqttService() {

// }