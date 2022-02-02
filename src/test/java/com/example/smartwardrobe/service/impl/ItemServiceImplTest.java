package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemServiceImplTest {
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

//    @ParameterizedTest
//    @ValueSource(strings = {"color", "white", "black"})
//    void testGetDirtyItems(String category) {
//        when(this.itemRepository.findItemIfDirty()).thenReturn(new ArrayList<>());
//        assertTrue(this.itemServiceImpl.getDirtyItems(category).isEmpty());
//        verify(this.itemRepository).findItemIfDirty();
//    }

//    @Test
//    void testGetDirtyItems2() {
//        Item item = new Item();
//        item.setCode("white");
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
//        ArrayList<Item> itemList = new ArrayList<>();
//        itemList.add(item);
//
//        when(this.itemRepository.findItemIfDirty()).thenReturn(itemList);
//        assertEquals(1, this.itemServiceImpl.getDirtyItems("Color").size());
//        verify(this.itemRepository).findItemIfDirty();
//    }
//
//    @Test
//    void testGetDirtyItems3() {
//        Item item = new Item();
//        item.setCode("white");
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
//        item.setWashingZoneColor(WashingZoneColor.BLACK);
//
//        Item item1 = new Item();
//        item1.setCode("WHITE");
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
//        item1.setWashingZoneColor(WashingZoneColor.COLOR);
//        ArrayList<Item> itemList = new ArrayList<>();
//        itemList.add(item1);
//        itemList.add(item);
//
//        when(this.itemRepository.findItemIfDirty()).thenReturn(itemList);
//        assertEquals(1, this.itemServiceImpl.getDirtyItems("Color").size());
//        verify(this.itemRepository).findItemIfDirty();
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

}

