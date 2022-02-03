package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.enums.EyeColor;
import com.example.smartwardrobe.enums.Gender;
import com.example.smartwardrobe.enums.HairColor;
import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.ItemColor;
import com.example.smartwardrobe.enums.Material;
import com.example.smartwardrobe.enums.Size;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.enums.WashingZoneColor;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.model.dto.ItemDto;
import com.example.smartwardrobe.repository.ItemRepository;
import com.example.smartwardrobe.service.UserService;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.text.StrBuilder;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemServiceImplTest {
    @MockBean
    private UserService userService;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Test
    void testSaveItem() {
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

        when(this.itemRepository.save(any())).thenReturn(item);

        Item item1 = new Item();
        item1.setCode("Code");
        item1.setId(123L);
        item1.setItemCategory(ItemCategory.JEANS);
        item1.setItemColor(ItemColor.MOHOGAMY);
        item1.setLastWashingDay(LocalDate.ofEpochDay(1L));
        item1.setLastWearing(LocalDate.ofEpochDay(1L));
        item1.setMaterial(Material.WOOL);
        item1.setNrOfWearsSinceLastWash(1);
        item1.setOutfits(new ArrayList<>());
        item1.setSize(Size.M);
        item1.setStyle(Style.CASUAL);
        item1.setWashingZoneColor(WashingZoneColor.COLOR);

        assertSame(item, this.itemServiceImpl.saveItem(item1));
        verify(this.itemRepository).save(any());
    }

    @Test
    void testFindItemById() {
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
        Optional<Item> ofResult = Optional.of(item);

        when(this.itemRepository.findById(any())).thenReturn(ofResult);

        assertSame(item, this.itemServiceImpl.findItemById(123L));
        verify(this.itemRepository).findById(any());
    }

    @Test
    void testFindItemsByCategory() {
        ArrayList<Item> itemList = new ArrayList<>();
        when(this.itemRepository.findByItemCategory(any())).thenReturn(itemList);
        List<Item> actualFindItemsByCategoryResult = this.itemServiceImpl.findItemsByCategory(ItemCategory.JEANS);

        assertSame(itemList, actualFindItemsByCategoryResult);
        assertTrue(actualFindItemsByCategoryResult.isEmpty());
        verify(this.itemRepository).findByItemCategory(any());
    }

    @Test
    void testFindItemIfDirty() {
        ArrayList<Item> itemList = new ArrayList<>();
        when(this.itemRepository.findItemIfDirty()).thenReturn(itemList);
        List<Item> actualFindItemIfDirtyResult = this.itemServiceImpl.findItemIfDirty();
        assertSame(itemList, actualFindItemIfDirtyResult);
        assertTrue(actualFindItemIfDirtyResult.isEmpty());
        verify(this.itemRepository).findItemIfDirty();
    }

    @Test
    void testDeleteItemById() {
        doNothing().when(this.itemRepository).deleteById(anyLong());
        this.itemServiceImpl.deleteItemById(123L);
        verify(this.itemRepository).deleteById(anyLong());
    }

    @Test
    void testFindAllItems() {
        ArrayList<Item> itemList = new ArrayList<>();

        when(this.itemRepository.findAll()).thenReturn(itemList);

        List<Item> actualFindAllItemsResult = this.itemServiceImpl.findAllItems();

        assertSame(itemList, actualFindAllItemsResult);
        assertTrue(actualFindAllItemsResult.isEmpty());
        verify(this.itemRepository).findAll();
        assertTrue(this.itemServiceImpl.findItemIfDirty().isEmpty());
    }

    @Test
    void testGetItemsByStyleName() {
        assertTrue(this.itemServiceImpl.getItemsByStyleName("Style Name").isEmpty());
    }

    @Test
    void testWashItem() {
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
        Optional<Item> ofResult = Optional.of(item);

        Item item1 = new Item();
        item1.setCode("Code");
        item1.setId(123L);
        item1.setItemCategory(ItemCategory.JEANS);
        item1.setItemColor(ItemColor.MOHOGAMY);
        item1.setLastWashingDay(LocalDate.ofEpochDay(1L));
        item1.setLastWearing(LocalDate.ofEpochDay(1L));
        item1.setMaterial(Material.WOOL);
        item1.setNrOfWearsSinceLastWash(1);
        item1.setOutfits(new ArrayList<>());
        item1.setSize(Size.M);
        item1.setStyle(Style.CASUAL);
        item1.setWashingZoneColor(WashingZoneColor.COLOR);

        when(this.itemRepository.save(any())).thenReturn(item1);
        when(this.itemRepository.findById(any())).thenReturn(ofResult);
        assertSame(item1, this.itemServiceImpl.washItem("42"));
        verify(this.itemRepository).findById(any());
        verify(this.itemRepository).save(any());
    }

    @Test
    void testUpdateItemAfterAddingOutfit() {
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
        Optional<Item> ofResult = Optional.of(item);

        Item item1 = new Item();
        item1.setCode("Code");
        item1.setId(123L);
        item1.setItemCategory(ItemCategory.JEANS);
        item1.setItemColor(ItemColor.MOHOGAMY);
        item1.setLastWashingDay(LocalDate.ofEpochDay(1L));
        item1.setLastWearing(LocalDate.ofEpochDay(1L));
        item1.setMaterial(Material.WOOL);
        item1.setNrOfWearsSinceLastWash(1);
        item1.setOutfits(new ArrayList<>());
        item1.setSize(Size.M);
        item1.setStyle(Style.CASUAL);
        item1.setWashingZoneColor(WashingZoneColor.COLOR);

        when(this.itemRepository.save(any())).thenReturn(item1);
        when(this.itemRepository.findById(any())).thenReturn(ofResult);
        assertSame(item1, this.itemServiceImpl.updateItemAfterAddingOutfit(123L));
        verify(this.itemRepository).findById(any());
        verify(this.itemRepository).save(any());
    }

    @Test
    void testGetDirtyItemsByColor() {
        ArrayList<Item> itemList = new ArrayList<>();
        when(this.itemRepository.findItemIfDirty()).thenReturn(itemList);
        Pair<List<Item>, Set<JSONObject>> actualDirtyItemsByColor = this.itemServiceImpl.getDirtyItemsByColor("Color");
        assertEquals(itemList, actualDirtyItemsByColor.getFirst());
        assertTrue(actualDirtyItemsByColor.getSecond().isEmpty());
        verify(this.itemRepository).findItemIfDirty();
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testGetDirtyItemsByColor2() {
        when(this.itemRepository.findItemIfDirty()).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> this.itemServiceImpl.getDirtyItemsByColor("Color"));
        verify(this.itemRepository).findItemIfDirty();
    }

    @Test
    void testGetDirtyItemsByColor3() {
        Item item = new Item();
        item.setCode("src/main/java/com/example/smartwardrobe/json/wash_instructions.json");
        item.setId(123L);
        item.setItemCategory(ItemCategory.JEANS);
        item.setItemColor(ItemColor.MOHOGAMY);
        item.setLastWashingDay(LocalDate.ofEpochDay(0L));
        item.setLastWearing(LocalDate.ofEpochDay(0L));
        item.setMaterial(Material.WOOL);
        item.setNrOfWearsSinceLastWash(0);
        ArrayList<Outfit> outfitList = new ArrayList<>();
        item.setOutfits(outfitList);
        item.setSize(Size.M);
        item.setStyle(Style.CASUAL);
        item.setWashingZoneColor(WashingZoneColor.BLACK);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(this.itemRepository.findItemIfDirty()).thenReturn(itemList);
        Pair<List<Item>, Set<JSONObject>> actualDirtyItemsByColor = this.itemServiceImpl.getDirtyItemsByColor("Color");
        assertEquals(outfitList, actualDirtyItemsByColor.getFirst());
        assertTrue(actualDirtyItemsByColor.getSecond().isEmpty());
        verify(this.itemRepository).findItemIfDirty();
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

//    @Test
//    void testGetDirtyItemsByColor4() {
//        Item item = new Item();
//        item.setCode("src/main/java/com/example/smartwardrobe/json/wash_instructions.json");
//        item.setId(123L);
//        item.setItemCategory(ItemCategory.JEANS);
//        item.setItemColor(ItemColor.MOHOGAMY);
//        item.setLastWashingDay(LocalDate.ofEpochDay(0L));
//        item.setLastWearing(LocalDate.ofEpochDay(0L));
//        item.setMaterial(Material.WOOL);
//        item.setNrOfWearsSinceLastWash(0);
//        item.setOutfits(new ArrayList<>());
//        item.setSize(Size.M);
//        item.setStyle(Style.CASUAL);
//        item.setWashingZoneColor(WashingZoneColor.COLOR);
//
//        Item item1 = new Item();
//        item1.setCode("src/main/java/com/example/smartwardrobe/json/wash_instructions.json");
//        item1.setId(123L);
//        item1.setItemCategory(ItemCategory.JEANS);
//        item1.setItemColor(ItemColor.MOHOGAMY);
//        item1.setLastWashingDay(LocalDate.ofEpochDay(0L));
//        item1.setLastWearing(LocalDate.ofEpochDay(0L));
//        item1.setMaterial(Material.WOOL);
//        item1.setNrOfWearsSinceLastWash(0);
//        item1.setOutfits(new ArrayList<>());
//        item1.setSize(Size.M);
//        item1.setStyle(Style.CASUAL);
//        item1.setWashingZoneColor(WashingZoneColor.BLACK);
//
//        ArrayList<Item> itemList = new ArrayList<>();
//        itemList.add(item1);
//        itemList.add(item);
//        when(this.itemRepository.findItemIfDirty()).thenReturn(itemList);
//        Pair<List<Item>, Set<JSONObject>> actualDirtyItemsByColor = this.itemServiceImpl.getDirtyItemsByColor("Color");
//        assertEquals(1, actualDirtyItemsByColor.getFirst().size());
//        assertEquals(
//                "[{\"id\":123,\"material\":\"WOOL\",\"size\":\"M\",\"code\":\"src/main/java/com/example/smartwardrobe/json/wash"
//                        + "_instructions.json\",\"itemColor\":\"MOHOGAMY\",\"style\":\"CASUAL\",\"itemCategory\":\"JEANS\",\"washingZoneColor"
//                        + "\":\"COLOR\"}]->[{\"WOOL_INSTRUCTIONS\":\"Dryclean only, use cool-warm iron, steam when pressing, brush wool"
//                        + " to remove surface soil.\",\"MAXTEMPERATURE\":\"40°C\"}]",
//                actualDirtyItemsByColor.toString());
//        assertEquals(1, actualDirtyItemsByColor.getSecond().size());
//        verify(this.itemRepository).findItemIfDirty();
//        assertTrue(this.itemServiceImpl.findAll().isEmpty());
//    }

    @Test
    void testCreateJsonArrayOfItems() {
        assertTrue(this.itemServiceImpl.createJsonArrayOfItems(new ArrayList<>()).isEmpty());
    }

    @Test
    void testCreateJsonArrayOfItems2() {
        Item item = new Item();
        item.setCode("Code");
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

        assertEquals(1, this.itemServiceImpl.createJsonArrayOfItems(itemList).size());
    }

    @Test
    void testCreateJsonObjectFromItem() {
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
        item.setWashingZoneColor(WashingZoneColor.BLACK);
        assertEquals(11, this.itemServiceImpl.createJsonObjectFromItem(item).size());
    }

    @Test
    void testCreateJsonObjectFromItem2() {
        LocalDate lastWearing = LocalDate.ofEpochDay(0L);
        LocalDate lastWashingDay = LocalDate.ofEpochDay(0L);

        Item item = new Item(123L, Material.WOOL, Size.M, "Code", ItemColor.MOHOGAMY, Style.CASUAL, ItemCategory.JEANS,
                lastWearing, lastWashingDay, 0, WashingZoneColor.BLACK, new ArrayList<>());
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
        item.setWashingZoneColor(WashingZoneColor.BLACK);
        assertEquals(11, this.itemServiceImpl.createJsonObjectFromItem(item).size());
    }

    @Test
    void testCreateJsonObjectFromItem3() {
        Item item = new Item();
        item.setCode("Code");
        item.setId(123L);
        item.setItemCategory(ItemCategory.JEANS);
        item.setItemColor(ItemColor.MOHOGAMY);
        item.setLastWashingDay(null);
        item.setLastWearing(LocalDate.ofEpochDay(1L));
        item.setMaterial(Material.WOOL);
        item.setNrOfWearsSinceLastWash(1);
        item.setOutfits(new ArrayList<>());
        item.setSize(Size.M);
        item.setStyle(Style.CASUAL);
        item.setWashingZoneColor(WashingZoneColor.BLACK);
        assertEquals(11, this.itemServiceImpl.createJsonObjectFromItem(item).size());
    }

    @Test
    void testCreateJsonObjectFromItem4() throws JsonProcessingException, ParseException, JSONException {
        Item item = new Item();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new ObjectMapper().writeValueAsString(item));
        JSONAssert.assertEquals(jsonObject.toJSONString(), this.itemServiceImpl.createJsonObjectFromItem(item).toJSONString(), false);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForIdAndItemCategory")
    void testConvertDtoToEntity(long id, String itemCategory) {
        ItemDto itemDto = new ItemDto();
        itemDto.setCode("Code");
        itemDto.setId(id);
        itemDto.setItemCategory(itemCategory);
        itemDto.setItemColor("Item Color");
        itemDto.setLastWashingDay("Last Washing Day");
        itemDto.setLastWearing("Last Wearing");
        itemDto.setMaterial("Material");
        itemDto.setNrOfWearsSinceLastWash(1);
        itemDto.setSize("Size");
        itemDto.setStyle("Style");
        itemDto.setWashingZoneColor("Washing Zone Color");
        assertThrows(IllegalArgumentException.class, () -> this.itemServiceImpl.convertDtoToEntity(itemDto));
    }

    @Test
    void testReadAllItemsFromStore() {
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
        item.setWashingZoneColor(WashingZoneColor.BLACK);
        when(this.itemRepository.save(any())).thenReturn(item);
        this.itemServiceImpl.readAllItemsFromStore();
        verify(this.itemRepository, atLeast(1)).save(any());
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testReadAllItemsFromStore2() {
        when(this.itemRepository.save(any()))
                .thenThrow(new DateTimeParseException("An error occurred", new StrBuilder(), -1));
        assertThrows(DateTimeParseException.class, () -> this.itemServiceImpl.readAllItemsFromStore());
        verify(this.itemRepository).save(any());
    }

    @Test
    void testReadAllItemsByCategoryFromStore() {
        assertEquals(24, this.itemServiceImpl.readAllItemsByCategoryFromStore(ItemCategory.JEANS).size());
        assertEquals(24, this.itemServiceImpl.readAllItemsByCategoryFromStore(ItemCategory.JEANS).size());
    }

    @Test
    void testSortItemsByLastWearingDate() {
        ArrayList<Item> itemList = new ArrayList<>();
        when(this.itemRepository.findAll()).thenReturn(itemList);
        List<Item> actualSortItemsByLastWearingDateResult = this.itemServiceImpl.sortItemsByLastWearingDate();
        assertSame(itemList, actualSortItemsByLastWearingDateResult);
        assertTrue(actualSortItemsByLastWearingDateResult.isEmpty());
        verify(this.itemRepository).findAll();
        assertTrue(this.itemServiceImpl.findItemIfDirty().isEmpty());
    }

    @Test
    void testSortItemsByLastWearingDate2() {
        when(this.itemRepository.findAll())
                .thenThrow(new DateTimeParseException("An error occurred", new StrBuilder(), -1));
        assertThrows(DateTimeParseException.class, () -> this.itemServiceImpl.sortItemsByLastWearingDate());
        verify(this.itemRepository).findAll();
    }

    @Test
    void testUpdateWardrobe() {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        when(this.userService.findUserById((Long) any())).thenReturn(user);
        when(this.itemRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.itemServiceImpl.updateWardrobe("42").isEmpty());
        verify(this.userService).findUserById((Long) any());
        verify(this.itemRepository).findAll();
        assertTrue(this.itemServiceImpl.findItemIfDirty().isEmpty());
    }

    @Test
    void testUpdateWardrobe2() {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        when(this.userService.findUserById((Long) any())).thenReturn(user);
        when(this.itemRepository.findAll())
                .thenThrow(new DateTimeParseException("An error occurred", new StrBuilder(), -1));
        assertThrows(DateTimeParseException.class, () -> this.itemServiceImpl.updateWardrobe("42"));
        verify(this.userService).findUserById((Long) any());
        verify(this.itemRepository).findAll();
    }

    @Test
    void testUpdateWardrobe3() {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        when(this.userService.calculateUserSize((User) any())).thenReturn(Size.M);
        when(this.userService.findUserById((Long) any())).thenReturn(user);

        Item item = new Item();
        item.setCode("Code");
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
        item.setWashingZoneColor(WashingZoneColor.BLACK);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(this.itemRepository.findAll()).thenReturn(itemList);
        assertTrue(this.itemServiceImpl.updateWardrobe("42").isEmpty());
        verify(this.userService).calculateUserSize((User) any());
        verify(this.userService).findUserById((Long) any());
        verify(this.itemRepository, atLeast(1)).findAll();
        assertTrue(this.itemServiceImpl.findItemIfDirty().isEmpty());
    }

    @Test
    void testUpdateWardrobe4() {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        when(this.userService.calculateUserSize((User) any()))
                .thenThrow(new DateTimeParseException("An error occurred", new StrBuilder(), -1));
        when(this.userService.findUserById((Long) any())).thenReturn(user);

        Item item = new Item();
        item.setCode("Code");
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
        item.setWashingZoneColor(WashingZoneColor.BLACK);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(this.itemRepository.findAll()).thenReturn(itemList);
        assertThrows(DateTimeParseException.class, () -> this.itemServiceImpl.updateWardrobe("42"));
        verify(this.userService).calculateUserSize((User) any());
        verify(this.userService).findUserById((Long) any());
        verify(this.itemRepository, atLeast(1)).findAll();
    }

    @Test
    void testUpdateWardrobe5() {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        when(this.userService.calculateUserSize((User) any())).thenReturn(Size.L);
        when(this.userService.findUserById((Long) any())).thenReturn(user);

        Item item = new Item();
        item.setCode("Code");
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
        item.setWashingZoneColor(WashingZoneColor.BLACK);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(this.itemRepository.findAll()).thenReturn(itemList);
        assertEquals(1, this.itemServiceImpl.updateWardrobe("42").size());
        verify(this.userService).calculateUserSize((User) any());
        verify(this.userService).findUserById((Long) any());
        verify(this.itemRepository, atLeast(1)).findAll();
        assertTrue(this.itemServiceImpl.findItemIfDirty().isEmpty());
    }

    private static Stream<Arguments> provideArgsForIdAndItemCategory() {
        return Stream.of(Arguments.of(123L, "Item Category"), Arguments.of(0L, "ItemCategory"), Arguments.of(123L, null));
    }

    @Test
    void testFindByStyleName(){
        List<Item> itemList = new ArrayList<>();
        when(this.itemRepository.findByStyle(any())).thenReturn(itemList);
        List<Item> result = itemServiceImpl.getItemsByStyleName("style");
        assertTrue(result.isEmpty());
        assertThat(result).hasSameElementsAs(itemList);
    }

    @Test
    void testConvertJsonObjectToItemOrCoat() throws JsonProcessingException, ParseException {
        JSONObject jsonObjectItem = new JSONObject();
        jsonObjectItem.put("material", "CASHMERE");
        jsonObjectItem.put("size", "S");
        jsonObjectItem.put("code", "123");
        jsonObjectItem.put("itemColor", "RED");
        jsonObjectItem.put("style", "CASUAL");
        jsonObjectItem.put("itemCategory", "DRESS");
        jsonObjectItem.put("washingZoneColor", "WHITE");

        Item item = new Item();
        item.setWashingZoneColor(WashingZoneColor.WHITE);
        item.setItemColor(ItemColor.RED);
        item.setItemCategory(ItemCategory.DRESS);
        item.setStyle(Style.CASUAL);
        item.setMaterial(Material.CASHMERE);
        item.setSize(Size.S);
        item.setCode("123");

        JSONObject jsonObjectCoat = new JSONObject();
        jsonObjectCoat.put("material", "CASHMERE");
        jsonObjectCoat.put("size", "S");
        jsonObjectCoat.put("code", "123");
        jsonObjectCoat.put("itemColor", "RED");
        jsonObjectCoat.put("style", "CASUAL");
        jsonObjectCoat.put("coatCategory", "JACKET");
        jsonObjectCoat.put("washingZoneColor", "WHITE");

        Coat coat = new Coat();
        coat.setWashingZoneColor(WashingZoneColor.WHITE);
        coat.setItemColor(ItemColor.RED);
        coat.setCoatCategory(CoatCategory.JACKET);
        coat.setStyle(Style.CASUAL);
        coat.setMaterial(Material.CASHMERE);
        coat.setSize(Size.S);
        coat.setCode("123");

        Item itemResult = (Item) this.itemServiceImpl.convertJsonObjectToItemOrCoat(jsonObjectItem, 1);
        Coat coatResult = (Coat) this.itemServiceImpl.convertJsonObjectToItemOrCoat(jsonObjectCoat, 2);

        assertThat(itemResult).usingRecursiveComparison().isEqualTo(item);
        assertThat(coatResult).usingRecursiveComparison().isEqualTo(coat);
    }
}

