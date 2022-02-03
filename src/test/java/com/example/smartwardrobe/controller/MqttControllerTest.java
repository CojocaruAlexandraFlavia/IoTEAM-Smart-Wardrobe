package com.example.smartwardrobe.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.mqtt.MqttPublishModel;
import com.example.smartwardrobe.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;

@ContextConfiguration(classes = {MqttController.class})
@ExtendWith(SpringExtension.class)
class MqttControllerTest {
    @MockBean
    private ItemService itemService;

    @Autowired
    private MqttController mqttController;

    @ParameterizedTest
    @ValueSource(strings = {"/api/mqtt/publish/AllDirtyClothes", "/api/mqtt/publish/Items", "/api/mqtt/publish/WeatherConditions"})
    void testPublish(String url) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
        MockMvcBuilders.standaloneSetup(this.mqttController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testPublishMessage() {
        MqttPublishModel mqttPublishModel = new MqttPublishModel();
        mqttPublishModel.setMessage("Not all who wander are lost");
        mqttPublishModel.setQos(1);
        mqttPublishModel.setRetained(true);
        mqttPublishModel.setTopic("Topic");
        BeanPropertyBindingResult beanPropertyBindingResult = mock(BeanPropertyBindingResult.class);
        when(beanPropertyBindingResult.hasErrors()).thenReturn(true);
        assertThrows(com.example.smartwardrobe.exceptions.MqttException.class,
                () -> MqttController.publishMessage(mqttPublishModel,
                        new BindException(new BindException(new BindException(new BindException(beanPropertyBindingResult))))));
        verify(beanPropertyBindingResult).hasErrors();
    }

    @Test
    void testPublishAllDirtyClothes2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/mqtt/publish/AllDirtyClothes",
                "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.mqttController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testPublishItems2() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/mqtt/publish/Items");
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.mqttController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testPublishWeatherConditions2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/mqtt/publish/WeatherConditions",
                "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.mqttController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testPublishWeatherConditions3() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/mqtt/publish/WeatherConditions");
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.mqttController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSubscribeChannel() throws Exception {
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get("/api/mqtt/subscribe").param("topic", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("waitMillis", String.valueOf(1));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.mqttController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

