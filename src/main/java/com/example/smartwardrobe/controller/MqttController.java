package com.example.smartwardrobe.controller;


import com.example.smartwardrobe.exceptions.MqttException;
import com.example.smartwardrobe.mqtt.Mqtt;
import com.example.smartwardrobe.mqtt.MqttPublishModel;
import com.example.smartwardrobe.mqtt.MqttSubscribeModel;
import com.example.smartwardrobe.service.ItemService;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/mqtt")
public class MqttController {

    static Logger logger = LoggerFactory.getLogger(MqttController.class);

    @Autowired
    private ItemService itemService;

    @PostMapping("/publish")
    public static void publishMessage(@RequestBody @Valid MqttPublishModel messagePublishModel,
                               BindingResult bindingResult) throws org.eclipse.paho.client.mqttv3.MqttException {
        if (bindingResult.hasErrors()) {
            throw new MqttException("SOME_PARAMETERS_INVALID");
        }

        MqttMessage mqttMessage = new MqttMessage(messagePublishModel.getMessage().getBytes());
        mqttMessage.setQos(messagePublishModel.getQos());
        mqttMessage.setRetained(messagePublishModel.getRetained());
        Mqtt.getInstance().publish(messagePublishModel.getTopic(), mqttMessage);
        logger.info("Message published");
    }

    @PostMapping("publish/Items")
    public void publishItems()
    {
        final long MINUTES = 1; // The delay in minutes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                String topic = "items";
                String message = itemService.findAllItems().toString();
                createMessagePublishModel(topic, message);
                logger.info("All items published successfully!");
            }

            }, 0, 1000 * 60 * MINUTES);

    }

    public static void createMessagePublishModel(String topic, @NotNull String message) throws org.eclipse.paho.client.mqttv3.MqttException {
        MqttPublishModel messagePublishModel = new MqttPublishModel();
        messagePublishModel.setTopic(topic);
        messagePublishModel.setQos(0);
        messagePublishModel.setRetained(true);
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(messagePublishModel.getQos());
        mqttMessage.setRetained(messagePublishModel.getRetained());
        Mqtt.getInstance().publish(topic, mqttMessage);
    }

    @PostMapping("/publish/AllDirtyClothes")
    public void publishAllDirtyClothes() {
        final long  MINUTES = 1; // The delay in minutes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() { // Function runs every MINUTES minutes.
                String topic = "store";
                String message = itemService.findItemIfDirty().toString();
                createMessagePublishModel(topic, message);
                logger.info("AllDirtyClothes published successfully!");
            }
        }, 0, 1000*60*MINUTES);
    }

    @PostMapping("/publish/WeatherConditions")
    public static void publishWeatherConditions() {
        final long MINUTES = 2; // The delay in minutes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                String topic = "weather";
                String message = WeatherController.getWeatherConditions();
                createMessagePublishModel(topic, message);
                logger.info("Weather conditions published successfully!");
            }
        }, 0, 1000 * 60 * MINUTES);
    }

    @GetMapping("subscribe")
    public List<MqttSubscribeModel> subscribeChannel(@RequestParam(value = "topic") String topic,
                                                     @RequestParam(value = "wait_millis") Integer waitMillis)
            throws InterruptedException, org.eclipse.paho.client.mqttv3.MqttException {
        List<MqttSubscribeModel> messages = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Mqtt.getInstance().subscribeWithResponse(topic, (s, mqttMessage) -> {
            MqttSubscribeModel mqttSubscribeModel = new MqttSubscribeModel();
            mqttSubscribeModel.setId(mqttMessage.getId());
            mqttSubscribeModel.setMessage(new String(mqttMessage.getPayload()));
            mqttSubscribeModel.setQos(mqttMessage.getQos());
            messages.add(mqttSubscribeModel);
            countDownLatch.countDown();
        });

        countDownLatch.await(waitMillis, TimeUnit.MILLISECONDS);

        return messages;
    }


}