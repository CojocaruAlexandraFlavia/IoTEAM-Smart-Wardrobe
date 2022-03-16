package com.example.smartwardrobe.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.enums.ItemColor;
import com.example.smartwardrobe.enums.Material;
import com.example.smartwardrobe.enums.Size;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.enums.WashingZoneColor;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.model.dto.CoatDto;
import com.example.smartwardrobe.service.CoatService;
import com.example.smartwardrobe.service.OutfitService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

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

@ContextConfiguration(classes = {CoatController.class})
@ExtendWith(SpringExtension.class)
class CoatControllerTest {
    @Autowired
    private CoatController coatController;

    @MockBean
    private CoatService coatService;

    @MockBean
    private OutfitService outfitService;

    @Test
    void testWashItem() throws Exception {
        when(this.coatService.washItem(any())).thenReturn("Wash Item");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/coat/washing/{itemId}", "42");
        MockMvcBuilders.standaloneSetup(this.coatController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Wash Item"));
    }

    @Test
    void testWashItem2() throws Exception {
        when(this.coatService.washItem(any())).thenReturn("Wash Item");
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/coat/washing/{itemId}", "42");
        patchResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.coatController)
                .build()
                .perform(patchResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Wash Item"));
    }

    @Test
    void testReadAllCoatsFromStore() throws Exception {
        doNothing().when(this.coatService).readAllCoatsFromStore();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/coat/getAllStore");
        MockMvcBuilders.standaloneSetup(this.coatController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testReadAllCoatsFromStore2() throws Exception {
        doNothing().when(this.coatService).readAllCoatsFromStore();
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/coat/getAllStore");
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.coatController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testReadAllCoatsByCategoryFromStoreWhenEmpty() throws Exception {
        List<Coat> coats = new ArrayList<>();
        when(this.coatService.readAllCoatsByCategoryFromStore(any())).thenReturn(coats);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.
                get("/coat/getAllCoatsByCategory/{category}", "JACKET");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.coatController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void testReadAllCoatsByCategoryFromStoreWhenNotEmpty() throws Exception {
        List<Coat> coats = new ArrayList<>();
        Coat coat = new Coat();
        coat.setWashingZoneColor(WashingZoneColor.WHITE);
        coat.setItemColor(ItemColor.RED);
        coat.setCoatCategory(CoatCategory.JACKET);
        coat.setStyle(Style.CASUAL);
        coat.setMaterial(Material.CASHMERE);
        coat.setSize(Size.S);
        coat.setCode("123");
        coats.add(coat);
        when(this.coatService.readAllCoatsByCategoryFromStore(any())).thenReturn(coats);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.
                get("/coat/getAllCoatsByCategory/{category}", "JACKET");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.coatController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }

}

