package com.example.smartwardrobe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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
import com.example.smartwardrobe.repository.ItemRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemServiceImplTest {
    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Test
    void testSaveItem() {
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
        when(this.itemRepository.save(any())).thenReturn(item);

        User user1 = new User();
        user1.setAge(1);
        user1.setEyeColor(EyeColor.BLUE);
        user1.setGender(Gender.MALE);
        user1.setHairColor(HairColor.BROWN);
        user1.setHeight(10.0);
        user1.setId(123L);
        //user1.setItems(new ArrayList<>());
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");
        user1.setWeight(10.0);

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
        //item1.setUser(user1);
        item1.setWashingZoneColor(WashingZoneColor.COLOR);
        assertSame(item, this.itemServiceImpl.saveItem(item1));
        verify(this.itemRepository).save(any());
    }

    @Test
    void testFindItemById() {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
       // user.setItems(new ArrayList<>());
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
        Optional<Item> ofResult = Optional.of(item);
        when(this.itemRepository.findById(any())).thenReturn(ofResult);
        assertSame(item, this.itemServiceImpl.findItemById(123L));
        verify(this.itemRepository).findById(any());
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testFindItemsByCategory() {
        ArrayList<Item> itemList = new ArrayList<>();
        when(this.itemRepository.findByItemCategory(any())).thenReturn(itemList);
        List<Item> actualFindItemsByCategoryResult = this.itemServiceImpl.findItemsByCategory(ItemCategory.JEANS);
        assertSame(itemList, actualFindItemsByCategoryResult);
        assertTrue(actualFindItemsByCategoryResult.isEmpty());
        verify(this.itemRepository).findByItemCategory(any());
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testFindItemIfDirty() {
        ArrayList<Item> itemList = new ArrayList<>();
        when(this.itemRepository.findItemIfDirty()).thenReturn(itemList);
        List<Item> actualFindItemIfDirtyResult = this.itemServiceImpl.findItemIfDirty();
        assertSame(itemList, actualFindItemIfDirtyResult);
        assertTrue(actualFindItemIfDirtyResult.isEmpty());
        verify(this.itemRepository).findItemIfDirty();
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testDeleteItemById() {
        doNothing().when(this.itemRepository).deleteById((Long) any());
        this.itemServiceImpl.deleteItemById(123L);
        verify(this.itemRepository).deleteById((Long) any());
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
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
        Optional<Item> ofResult = Optional.of(item);

        User user1 = new User();
        user1.setAge(1);
        user1.setEyeColor(EyeColor.BLUE);
        user1.setGender(Gender.MALE);
        user1.setHairColor(HairColor.BROWN);
        user1.setHeight(10.0);
        user1.setId(123L);
        //user1.setItems(new ArrayList<>());
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");
        user1.setWeight(10.0);

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
        //item1.setUser(user1);
        item1.setWashingZoneColor(WashingZoneColor.COLOR);
        when(this.itemRepository.save(any())).thenReturn(item1);
        when(this.itemRepository.findById(any())).thenReturn(ofResult);
        assertSame(item1, this.itemServiceImpl.washItem("42"));
        verify(this.itemRepository).findById(any());
        verify(this.itemRepository).save(any());
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testUpdateItemAfterAddingOutfit() {
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
        Optional<Item> ofResult = Optional.of(item);

        User user1 = new User();
        user1.setAge(1);
        user1.setEyeColor(EyeColor.BLUE);
        user1.setGender(Gender.MALE);
        user1.setHairColor(HairColor.BROWN);
        user1.setHeight(10.0);
        user1.setId(123L);
        //user1.setItems(new ArrayList<>());
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");
        user1.setWeight(10.0);

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
        //item1.setUser(user1);
        item1.setWashingZoneColor(WashingZoneColor.COLOR);
        when(this.itemRepository.save(any())).thenReturn(item1);
        when(this.itemRepository.findById(any())).thenReturn(ofResult);
        assertSame(item1, this.itemServiceImpl.updateItemAfterAddingOutfit(123L));
        verify(this.itemRepository).findById(any());
        verify(this.itemRepository).save(any());
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testGetDirtyItems() {
        when(this.itemRepository.findItemIfDirty()).thenReturn(new ArrayList<>());
        assertTrue(this.itemServiceImpl.getDirtyItems("Color").isEmpty());
        verify(this.itemRepository).findItemIfDirty();
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testGetDirtyItems2() {
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
        item.setCode("white");
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
        when(this.itemRepository.findItemIfDirty()).thenReturn(itemList);
        assertEquals(1, this.itemServiceImpl.getDirtyItems("Color").size());
        verify(this.itemRepository).findItemIfDirty();
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testGetDirtyItems3() {
        when(this.itemRepository.findItemIfDirty()).thenReturn(new ArrayList<>());
        assertTrue(this.itemServiceImpl.getDirtyItems("white").isEmpty());
        verify(this.itemRepository).findItemIfDirty();
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testGetDirtyItems4() {
        when(this.itemRepository.findItemIfDirty()).thenReturn(new ArrayList<>());
        assertTrue(this.itemServiceImpl.getDirtyItems("black").isEmpty());
        verify(this.itemRepository).findItemIfDirty();
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testGetDirtyItems5() {
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
        item.setCode("white");
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
        item.setWashingZoneColor(WashingZoneColor.BLACK);

        User user1 = new User();
        user1.setAge(0);
        user1.setEyeColor(EyeColor.BLUE);
        user1.setGender(Gender.MALE);
        user1.setHairColor(HairColor.BROWN);
        user1.setHeight(10.0);
        user1.setId(123L);
        //user1.setItems(new ArrayList<>());
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");
        user1.setWeight(10.0);

        Item item1 = new Item();
        item1.setCode("WHITE");
        item1.setId(123L);
        item1.setItemCategory(ItemCategory.JEANS);
        item1.setItemColor(ItemColor.MOHOGAMY);
        item1.setLastWashingDay(LocalDate.ofEpochDay(0L));
        item1.setLastWearing(LocalDate.ofEpochDay(0L));
        item1.setMaterial(Material.WOOL);
        item1.setNrOfWearsSinceLastWash(0);
        item1.setOutfits(new ArrayList<>());
        item1.setSize(Size.M);
        item1.setStyle(Style.CASUAL);
        //item1.setUser(user1);
        item1.setWashingZoneColor(WashingZoneColor.COLOR);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item);
        when(this.itemRepository.findItemIfDirty()).thenReturn(itemList);
        assertEquals(1, this.itemServiceImpl.getDirtyItems("Color").size());
        verify(this.itemRepository).findItemIfDirty();
        assertTrue(this.itemServiceImpl.findAll().isEmpty());
    }

    @Test
    void testCreateJsonArrayOfItems() {
        assertTrue(this.itemServiceImpl.createJsonArrayOfItems(new ArrayList<>()).isEmpty());
    }

    @Test
    void testCreateJsonArrayOfItems2() {
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
        //item.setUser(user);
        item.setWashingZoneColor(WashingZoneColor.COLOR);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        assertEquals(1, this.itemServiceImpl.createJsonArrayOfItems(itemList).size());
    }

//    @Test
//    void testCreateJsonObjectFromItem() {
//        User user = new User();
//        user.setAge(1);
//        user.setEyeColor(EyeColor.BLUE);
//        user.setGender(Gender.MALE);
//        user.setHairColor(HairColor.BROWN);
//        user.setHeight(10.0);
//        user.setId(123L);
//        //user.setItems(new ArrayList<>());
//        user.setPassword("iloveyou");
//        user.setUsername("janedoe");
//        user.setWeight(10.0);
//
//        Item item = new Item();
//        item.setCode("Code");
//        item.setId(123L);
//        item.setItemCategory(ItemCategory.JEANS);
//        item.setItemColor(ItemColor.MOHOGAMY);
//        item.setLastWashingDay(LocalDate.ofEpochDay(1L));
//        item.setLastWearing(LocalDate.ofEpochDay(1L));
//        item.setMaterial(Material.WOOL);
//        item.setNrOfWearsSinceLastWash(1);
//        item.setOutfits(new ArrayList<>());
//        item.setSize(Size.M);
//        item.setStyle(Style.CASUAL);
//        //item.setUser(user);
//        item.setWashingZoneColor(WashingZoneColor.COLOR);
//        assertEquals(10, this.itemServiceImpl.createJsonObjectFromItem(item).size());
//    }
//
//    @Test
//    void testCreateJsonObjectFromItem2() {
//        User user = new User();
//        user.setAge(1);
//        user.setEyeColor(EyeColor.BLUE);
//        user.setGender(Gender.MALE);
//        user.setHairColor(HairColor.BROWN);
//        user.setHeight(10.0);
//        user.setId(123L);
//        //user.setItems(new ArrayList<>());
//        user.setPassword("iloveyou");
//        user.setUsername("janedoe");
//        user.setWeight(10.0);
//
//        Item item = new Item();
//        item.setCode("Code");
//        item.setId(123L);
//        item.setItemCategory(ItemCategory.JEANS);
//        item.setItemColor(ItemColor.MOHOGAMY);
//        item.setLastWashingDay(LocalDate.ofEpochDay(1L));
//        item.setLastWearing(LocalDate.ofEpochDay(1L));
//        item.setMaterial(Material.WOOL);
//        item.setNrOfWearsSinceLastWash(1);
//        item.setOutfits(new ArrayList<>());
//        item.setSize(Size.M);
//        item.setStyle(Style.CASUAL);
//        //item.setUser(user);
//        item.setWashingZoneColor(WashingZoneColor.COLOR);
//        assertEquals(11, this.itemServiceImpl.createJsonObjectFromItem(item).size());
//    }
//
//    @Test
//    void testCreateJsonObjectFromItem3() {
//        User user = new User();
//        user.setAge(1);
//        user.setEyeColor(EyeColor.BLUE);
//        user.setGender(Gender.MALE);
//        user.setHairColor(HairColor.BROWN);
//        user.setHeight(10.0);
//        user.setId(123L);
//        //user.setItems(new ArrayList<>());
//        user.setPassword("iloveyou");
//        user.setUsername("janedoe");
//        user.setWeight(10.0);
//
//        Item item = new Item(123L, Material.WOOL, Size.M, "Code", ItemColor.MOHOGAMY, Style.CASUAL, ItemCategory.JEANS,
//                LocalDate.ofEpochDay(0L), LocalDate.ofEpochDay(0L), 0, WashingZoneColor.COLOR);
//        item.setCode("Code");
//        item.setId(123L);
//        item.setItemCategory(ItemCategory.JEANS);
//        item.setItemColor(ItemColor.MOHOGAMY);
//        item.setLastWashingDay(LocalDate.ofEpochDay(1L));
//        item.setLastWearing(LocalDate.ofEpochDay(1L));
//        item.setMaterial(Material.WOOL);
//        item.setNrOfWearsSinceLastWash(1);
//        item.setOutfits(new ArrayList<>());
//        item.setSize(Size.M);
//        item.setStyle(Style.CASUAL);
//        //item.setUser(user);
//        item.setWashingZoneColor(WashingZoneColor.COLOR);
//        assertEquals(11, this.itemServiceImpl.createJsonObjectFromItem(item).size());
//    }
//
//    @Test
//    void testCreateJsonObjectFromItem4() {
//        User user = new User();
//        user.setAge(1);
//        user.setEyeColor(EyeColor.BLUE);
//        user.setGender(Gender.MALE);
//        user.setHairColor(HairColor.BROWN);
//        user.setHeight(10.0);
//        user.setId(123L);
//        //user.setItems(new ArrayList<>());
//        user.setPassword("iloveyou");
//        user.setUsername("janedoe");
//        user.setWeight(10.0);
//
//        Item item = new Item();
//        item.setCode("Code");
//        item.setId(123L);
//        item.setItemCategory(ItemCategory.JEANS);
//        item.setItemColor(ItemColor.MOHOGAMY);
//        item.setLastWashingDay(null);
//        item.setLastWearing(LocalDate.ofEpochDay(1L));
//        item.setMaterial(Material.WOOL);
//        item.setNrOfWearsSinceLastWash(1);
//        item.setOutfits(new ArrayList<>());
//        item.setSize(Size.M);
//        item.setStyle(Style.CASUAL);
//        //item.setUser(user);
//        item.setWashingZoneColor(WashingZoneColor.COLOR);
//        assertEquals(11, this.itemServiceImpl.createJsonObjectFromItem(item).size());
//    }


}

