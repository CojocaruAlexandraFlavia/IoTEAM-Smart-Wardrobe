package com.example.smartwardrobe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.enums.EyeColor;
import com.example.smartwardrobe.enums.Gender;
import com.example.smartwardrobe.enums.HairColor;
import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.ItemColor;
import com.example.smartwardrobe.enums.Material;
import com.example.smartwardrobe.enums.Size;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.enums.WashingZoneColor;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.service.ItemService;

import java.time.LocalDate;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//@ContextConfiguration(classes = {ItemController.class})
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {ItemController.class})

@ContextConfiguration(classes = {ItemController.class})
@ExtendWith(SpringExtension.class)
class ItemControllerTest {
    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService itemService;

    @Test
    void contextLoads() {
        assertThat(itemController).isNotNull();
    }

    @Test
    void testGetAllItems() throws Exception {
        when(this.itemService.findAllItems()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetDirtyItemsByColor() throws Exception {
        when(this.itemService.getDirtyItems(any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/findDirtyByColor/{color}",
                "Color");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetDirtyItemsByColor2() throws Exception {
        when(this.itemService.getDirtyItems(any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/item/findDirtyByColor/{color}", "Color");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testDeleteItemById() throws Exception {
        doNothing().when(this.itemService).deleteItemById(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/item/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteItemById2() throws Exception {
        doNothing().when(this.itemService).deleteItemById(any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/item/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testWashItem() throws Exception {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        //user.setItems(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);

        Item item = new Item();
        item.setCode("Code");
        item.setId(123L);
        item.setItemCategory(ItemCategory.JEANS);
        item.setItemColor(ItemColor.MOHOGAMY);
        item.setLastWashingDay(LocalDate.ofEpochDay(1L));
        item.setLastWearing(LocalDate.ofEpochDay(1L));
        item.setMaterial(Material.WOOL);
        item.setNrOfWearsSinceLastWash(1);
        item.setOutfits(new ArrayList<>());
        item.setSize(Size.M);
        item.setStyle(Style.CASUAL);
        //item.setUser(user);
        item.setWashingZoneColor(WashingZoneColor.COLOR);
        when(this.itemService.washItem(any())).thenReturn(item);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/item/washing/{itemId}", "42");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"material\":\"WOOL\",\"size\":\"M\",\"code\":\"Code\",\"itemColor\":\"MOHOGAMY\",\"style\":\"CASUAL\",\"itemCategory"
                                        + "\":\"JEANS\",\"lastWearing\":[1970,1,2],\"lastWashingDay\":[1970,1,2],\"nrOfWearsSinceLastWash\":1,\"washingZoneColor"
                                        + "\":\"COLOR\"}"));
    }

    @Test
    void testGetAllItems2() throws Exception {
        when(this.itemService.findAllItems()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/item");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetDirtyItems() throws Exception {
        when(this.itemService.findItemIfDirty()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/findDirty");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetDirtyItems2() throws Exception {
        when(this.itemService.findItemIfDirty()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/item/findDirty");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetItemById() throws Exception {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        //user.setItems(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);

        Item item = new Item();
        item.setCode("Code");
        item.setId(123L);
        item.setItemCategory(ItemCategory.JEANS);
        item.setItemColor(ItemColor.MOHOGAMY);
        item.setLastWashingDay(LocalDate.ofEpochDay(1L));
        item.setLastWearing(LocalDate.ofEpochDay(1L));
        item.setMaterial(Material.WOOL);
        item.setNrOfWearsSinceLastWash(1);
        item.setOutfits(new ArrayList<>());
        item.setSize(Size.M);
        item.setStyle(Style.CASUAL);
        //item.setUser(user);
        item.setWashingZoneColor(WashingZoneColor.COLOR);
        when(this.itemService.findItemById((Long) any())).thenReturn(item);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"material\":\"WOOL\",\"size\":\"M\",\"code\":\"Code\",\"itemColor\":\"MOHOGAMY\",\"style\":\"CASUAL\",\"itemCategory"
                                        + "\":\"JEANS\",\"lastWearing\":[1970,1,2],\"lastWashingDay\":[1970,1,2],\"nrOfWearsSinceLastWash\":1,\"washingZoneColor"
                                        + "\":\"COLOR\"}"));
    }

    @Test
    void testGetItemByStyle() throws Exception {
        when(this.itemService.getItemsByStyleName((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/getByStyle/{styleName}",
                "Style Name");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Style or items not found!"));
    }

    @Test
    void testGetItemByStyle2() throws Exception {
        User user = new User();
        user.setAge(0);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        //user.setItems(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);

        Item item = new Item();
        item.setCode("?");
        item.setId(123L);
        item.setItemCategory(ItemCategory.JEANS);
        item.setItemColor(ItemColor.MOHOGAMY);
        item.setLastWashingDay(LocalDate.ofEpochDay(0L));
        item.setLastWearing(LocalDate.ofEpochDay(0L));
        item.setMaterial(Material.WOOL);
        item.setNrOfWearsSinceLastWash(0);
        item.setOutfits(new ArrayList<>());
        item.setSize(Size.M);
        item.setStyle(Style.CASUAL);
        //item.setUser(user);
        item.setWashingZoneColor(WashingZoneColor.COLOR);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(this.itemService.getItemsByStyleName((String) any())).thenReturn(itemList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/getByStyle/{styleName}",
                "Style Name");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":123,\"material\":\"WOOL\",\"size\":\"M\",\"code\":\"?\",\"itemColor\":\"MOHOGAMY\",\"style\":\"CASUAL\",\"itemCategory"
                                        + "\":\"JEANS\",\"lastWearing\":[1970,1,1],\"lastWashingDay\":[1970,1,1],\"nrOfWearsSinceLastWash\":0,\"washingZoneColor"
                                        + "\":\"COLOR\"}]"));
    }
}

