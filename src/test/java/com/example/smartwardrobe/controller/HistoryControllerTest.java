package com.example.smartwardrobe.controller;

import static org.mockito.Mockito.when;

import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {HistoryController.class})
@ExtendWith(SpringExtension.class)
class HistoryControllerTest {
    @Autowired
    private HistoryController historyController;

    @MockBean
    private HistoryService historyService;

    @Test
    void testGetAllHistories() throws Exception {
        when(this.historyService.findAllHistories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/history");
        MockMvcBuilders.standaloneSetup(this.historyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllHistories2() throws Exception {
        when(this.historyService.findAllHistories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/history");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.historyController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetHistoryByDate() throws Exception {
        when(this.historyService.findAllHistories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/history/{date}", "", "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.historyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetHistoryById() throws Exception {
        when(this.historyService.findAllHistories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/history/{id}", "", "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.historyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

//    @Test
//    void testSaveHistory() throws Exception {
//        when(this.historyService.findAllHistories()).thenReturn(new ArrayList<>());
//
//        Outfit outfit = new Outfit();
//        outfit.setCoat(CoatCategory.JACKET);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(new ArrayList<>());
//
//        History history = new History();
//        history.setDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
//        history.setId(123L);
//        history.setOutfit(outfit);
//        String content = (new ObjectMapper()).writeValueAsString(history);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/history")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//        MockMvcBuilders.standaloneSetup(this.historyController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("[]"));
//    }
}

