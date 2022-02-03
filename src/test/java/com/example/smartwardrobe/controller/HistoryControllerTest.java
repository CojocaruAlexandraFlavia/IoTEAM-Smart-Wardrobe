package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.dto.HistoryDto;
import com.example.smartwardrobe.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {HistoryController.class})
@ExtendWith(SpringExtension.class)
class HistoryControllerTest {
    @Autowired
    private HistoryController historyController;

    @MockBean
    private HistoryService historyService;

    @Test
    void testSaveHistory() throws Exception {
        when(this.historyService.saveHistory(any())).thenReturn(new History());

        HistoryDto historyDto = new HistoryDto();
        historyDto.setDatetime("2020-03-01");
        historyDto.setOutfitId(123L);
        String content = (new ObjectMapper()).writeValueAsString(historyDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.historyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"dateTime\":null,\"outfit\":null}"));
    }
}

