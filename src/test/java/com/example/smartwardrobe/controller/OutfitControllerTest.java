package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.exceptions.ItemException;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.dto.CoatDto;
import com.example.smartwardrobe.model.dto.OutfitDto;
import com.example.smartwardrobe.service.OutfitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
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
        doNothing().when(this.outfitService).deleteOutfitById(any());
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
        doNothing().when(this.outfitService).deleteOutfitById(any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/outfit/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetFavouriteOutfits() throws Exception {
        when(this.outfitService.findOutfitsWithBestRating(anyDouble())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/outfit/getFavouriteOutfits/{minimRating}", 10.0);
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetFavouriteOutfits2() throws Exception {
        when(this.outfitService.findOutfitsWithBestRating(anyDouble())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/outfit/getFavouriteOutfits/{minimRating}",
                10.0);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testRateOutfit() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/outfit/{outfitId}/{nrOfStars}", "",
                "Uri Vars", "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }

    @Test
    void testRateOutfit2() throws Exception {
        Coat coat = new Coat();
        coat.setCoatCategory(CoatCategory.JACKET);
        coat.setCode("Code");
        coat.setId(123L);
        coat.setItemColor(ItemColor.MOHOGAMY);
        coat.setLastWashingDay(LocalDate.ofEpochDay(1L));
        coat.setLastWearing(LocalDate.ofEpochDay(1L));
        coat.setMaterial(Material.WOOL);
        coat.setNrOfWearsSinceLastWash(1);
        coat.setOutfits(new ArrayList<>());
        coat.setSize(Size.M);
        coat.setStyle(Style.CASUAL);
        coat.setWashingZoneColor(WashingZoneColor.BLACK);

        Outfit outfit = new Outfit();
        outfit.setCoat(coat);
        outfit.setDescription("The characteristics of someone or something");
        outfit.setHistories(new ArrayList<>());
        outfit.setId(123L);
        outfit.setItems(new ArrayList<>());
        outfit.setNrOfReviews(1);
        outfit.setNrOfStars(1);
        outfit.setRating(10.0);
        when(this.outfitService.giveStarsToOutfit((String) any(), anyInt())).thenReturn(outfit);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/outfit/{outfitId}/{nrOfStars}",
                "Uri Vars", 0, "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"description\":\"The characteristics of someone or something\",\"nrOfStars\":1,\"nrOfReviews\":1,"
                                        + "\"rating\":10.0,\"coat\":{\"id\":123,\"material\":\"WOOL\",\"size\":\"M\",\"code\":\"Code\",\"itemColor\":\"MOHOGAMY\",\"style"
                                        + "\":\"CASUAL\",\"coatCategory\":\"JACKET\",\"lastWearing\":[1970,1,2],\"lastWashingDay\":[1970,1,2],\"nrOfWearsSi"
                                        + "nceLastWash\":1,\"washingZoneColor\":\"BLACK\"},\"items\":[]}"));
    }

    @Test
    void testRecommendMonochromaticOutfit() throws Exception {
        when(this.outfitService.recommendMonochromaticOutfit()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/recommendMonochromaticOutfit");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testRecommendMonochromaticOutfit2() throws Exception {
        when(this.outfitService.recommendMonochromaticOutfit()).thenThrow(new ItemException("?"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/recommendMonochromaticOutfit");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You need to buy more ?"));
    }

    @Test
    void testRecommendMonochromaticOutfit3() throws Exception {
        when(this.outfitService.recommendMonochromaticOutfit()).thenThrow(new Exception("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/recommendMonochromaticOutfit");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRecommendAnalogousOutfit() throws Exception {
        when(this.outfitService.recommendAnalogousOutfit()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/recommendAnalogousOutfit");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testRecommendAnalogousOutfit2() throws Exception {
        when(this.outfitService.recommendAnalogousOutfit()).thenThrow(new ItemException("?"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/recommendAnalogousOutfit");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You need to buy more ?"));
    }

    @Test
    void testRecommendAnalogousOutfit3() throws Exception {
        when(this.outfitService.recommendAnalogousOutfit()).thenThrow(new Exception("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/recommendAnalogousOutfit");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRecommendPastelOutfit() throws Exception {
        when(this.outfitService.recommendPastelOutfit()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/recommendPastelOutfit");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testRecommendPastelOutfit2() throws Exception {
        when(this.outfitService.recommendPastelOutfit()).thenThrow(new ItemException("?"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/recommendPastelOutfit");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You need to buy more ?"));
    }

    @Test
    void testRecommendPastelOutfit3() throws Exception {
        when(this.outfitService.recommendPastelOutfit()).thenThrow(new Exception("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/recommendPastelOutfit");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSelectRecommendedOutfit() throws Exception {
        doNothing().when(this.outfitService).selectRecommendedOutfit((Integer) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit/selectRecommendedOutfit/{id}",
                1);
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSelectRecommendedOutfit2() throws Exception {
        doNothing().when(this.outfitService).selectRecommendedOutfit((Integer) any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/outfit/selectRecommendedOutfit/{id}", 1);
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(postResult)
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

    @Test
    void testSaveOutfit() throws Exception {

        CoatDto coatDto = new CoatDto();
        coatDto.setCoatCategory("Coat Category");
        coatDto.setId("42");

        OutfitDto outfitDto = new OutfitDto();
        outfitDto.setCoat(coatDto);
        outfitDto.setDescription("The characteristics of someone or something");
        outfitDto.setItems(new ArrayList<>());
        outfitDto.setNrOfReviews(1);
        outfitDto.setNrOfStars(1);
        outfitDto.setRating(10.0);

        Outfit outfit = new Outfit();

        when(this.outfitService.saveOutfit(any())).thenReturn(outfit);

        String content = (new ObjectMapper()).writeValueAsString(outfitDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/outfit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.outfitController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"description\":null,\"nrOfStars\":0,\"nrOfReviews\":0,\"rating\":0.0,\"coat\":null,\"items\":null}"));
    }

}

