package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.model.dto.ItemDto;
import com.example.smartwardrobe.service.ItemService;
import com.example.smartwardrobe.service.UserService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemController.class})
@ExtendWith(SpringExtension.class)
class ItemControllerTest {
    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserService userService;

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
    void testSaveItem() throws Exception {
        when(this.itemService.findAllItems()).thenReturn(new ArrayList<>());

        ItemDto itemDto = new ItemDto();
        itemDto.setCode("Code");
        itemDto.setId(123L);
        itemDto.setItemCategory("Item Category");
        itemDto.setItemColor("Item Color");
        itemDto.setLastWashingDay("Last Washing Day");
        itemDto.setLastWearing("Last Wearing");
        itemDto.setMaterial("Material");
        itemDto.setNrOfWearsSinceLastWash(1);
        itemDto.setSize("Size");
        itemDto.setStyle("Style");
        itemDto.setWashingZoneColor("Washing Zone Color");
        String content = (new ObjectMapper()).writeValueAsString(itemDto);

        Item item = new Item();
        when(this.itemService.convertDtoToEntity(any())).thenReturn(item);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testWashItem() throws Exception {
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
        item.setWashingZoneColor(WashingZoneColor.COLOR);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(this.itemService.getItemsByStyleName(any())).thenReturn(itemList);
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

    @Test
    void testGetAllItemsFromStore() throws Exception {
        when(this.itemService.convertDtoToEntity(any())).thenReturn(new Item());
        when(this.itemService.saveItem(any())).thenReturn(new Item());

        itemService.readAllItemsFromStore();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item//getAllStore");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testReadAllItemsByCategoryFromStoreNotFound() throws Exception {

        List<Item> itemList = new ArrayList<>();
        Object object = new Object();
        when(this.itemService.readAllItemsByCategoryFromStore(any())).thenReturn(itemList);
        when(this.itemService.convertJsonObjectToItemOrCoat(any(), anyInt())).thenReturn(object);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/getAllItemsByCategory/{category}",
                "DRESS");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testReadAllItemsByCategoryFromStoreFound() throws Exception {

        List<Item> itemList = new ArrayList<>();
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
        item.setWashingZoneColor(WashingZoneColor.COLOR);
        itemList.add(item);
        Object object = new Object();
        when(this.itemService.readAllItemsByCategoryFromStore(any())).thenReturn(itemList);
        when(this.itemService.convertJsonObjectToItemOrCoat(any(), anyInt())).thenReturn(object);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/getAllItemsByCategory/{category}",
                "DRESS");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
                //.andExpect(MockMvcResultMatchers.content().string("[" + item + "]"));
    }

    @Test
    void testSortItemsByLastWearingDateNotFound() throws Exception {
        List<Item> itemList = new ArrayList<>();
        when(this.itemService.findAllItems()).thenReturn(itemList);
        when(this.itemService.sortItemsByLastWearingDate()).thenReturn(itemList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/sortByLastWearingDate");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testSortItemsByLastWearingDateFound() throws Exception {
        List<Item> itemList = new ArrayList<>();
        Item item = new Item();
        itemList.add(item);
        when(this.itemService.findAllItems()).thenReturn(itemList);
        when(this.itemService.sortItemsByLastWearingDate()).thenReturn(itemList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/item/sortByLastWearingDate");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateUserDetails() throws Exception {

        List<Item> itemList = new ArrayList<>();
        when(this.itemService.updateWardrobe(any())).thenReturn(itemList);
        when(this.itemService.findAllItems()).thenReturn(itemList);
        when(this.userService.findUserById(anyLong())).thenReturn(new User());
        when(this.userService.calculateUserSize(any())).thenReturn(Size.S);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/item/updateWardrobe/{userId}", "12");
        MockMvcBuilders.standaloneSetup(this.itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

}

