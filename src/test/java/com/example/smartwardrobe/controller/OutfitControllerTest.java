package com.example.smartwardrobe.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.service.OutfitService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        doNothing().when(this.outfitService).deleteOutfitById((Long) any());
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

//    @Test
//    void testGetOutfitById() throws Exception {
//        Outfit outfit = new Outfit();
//        outfit.setCoat(CoatCategory.JACKET);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(new ArrayList<>());
//        when(this.outfitService.findOutfitById((Long) any())).thenReturn(outfit);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/outfit/{id}", 123L);
//        MockMvcBuilders.standaloneSetup(this.outfitController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content()
//                        .string(
//                                "{\"id\":123,\"description\":\"The characteristics of someone or something\",\"coat\":\"JACKET\",\"items\":[]}"));
//    }

//    @Test
//    void testSaveOutfit() throws Exception {
//        when(this.outfitService.findAllOutfits()).thenReturn(new ArrayList<>());
//
//        Outfit outfit = new Outfit();
//        outfit.setCoat(CoatCategory.JACKET);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(new ArrayList<>());
//        String content = (new ObjectMapper()).writeValueAsString(outfit);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/outfit")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//        MockMvcBuilders.standaloneSetup(this.outfitController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("[]"));
//    }
}

