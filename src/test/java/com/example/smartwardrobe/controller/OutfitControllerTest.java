package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.service.OutfitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {OutfitController.class})
@ExtendWith(SpringExtension.class)
class OutfitControllerTest {
    @Autowired
    private OutfitController outfitController;

    @MockBean
    private OutfitService outfitService;

    @Test
    void testDeleteOutfitById() throws Exception {
        doNothing().when(this.outfitService).deleteOutfitById(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/outfit/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteOutfitById2() throws Exception {
        doNothing().when(this.outfitService).deleteOutfitById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/outfit/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteOutfitById3() throws Exception {
        doNothing().when(this.outfitService).deleteOutfitById(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/outfit/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteOutfitById4() throws Exception {
        doNothing().when(this.outfitService).deleteOutfitById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/outfit/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllOutfits() throws Exception {
        when(this.outfitService.findAllOutfits()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/outfit");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllOutfits2() throws Exception {
        when(this.outfitService.findAllOutfits()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/outfit");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

}

