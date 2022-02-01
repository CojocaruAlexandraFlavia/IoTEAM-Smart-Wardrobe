package com.example.smartwardrobe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.enums.CoatCategory;
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
import com.example.smartwardrobe.repository.OutfitRepository;
import com.example.smartwardrobe.service.HistoryService;
import com.example.smartwardrobe.service.ItemService;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONException;
import org.json.simple.JSONArray;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OutfitServiceImpl.class})
@ExtendWith(SpringExtension.class)
class OutfitServiceImplTest {
    @MockBean
    private HistoryService historyService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private OutfitRepository outfitRepository;

    @Autowired
    private OutfitServiceImpl outfitServiceImpl;

    @Test
    void testDeleteOutfitById() {
        doNothing().when(this.outfitRepository).deleteById(any());

        this.outfitServiceImpl.deleteOutfitById(123L);

        verify(this.outfitRepository).deleteById(any());
        assertTrue(this.outfitServiceImpl.findAllOutfits().isEmpty());
    }

    @Test
    void testFindOutfitById() {
        Coat coat = new Coat();
        coat.setCoatCategory(CoatCategory.JACKET);
        coat.setId(123L);
        coat.setOutfits(new ArrayList<>());

        Outfit outfit = new Outfit();
        outfit.setCoat(coat);
        outfit.setDescription("The characteristics of someone or something");
        outfit.setHistories(new ArrayList<>());
        outfit.setId(123L);
        outfit.setItems(new ArrayList<>());
        outfit.setNrOfReviews(1);
        outfit.setNrOfStars(1);
        outfit.setRating(10.0);
        Optional<Outfit> ofResult = Optional.of(outfit);
        when(this.outfitRepository.findById(any())).thenReturn(ofResult);
        assertSame(outfit, this.outfitServiceImpl.findOutfitById(123L));
        verify(this.outfitRepository).findById(any());
        assertTrue(this.outfitServiceImpl.findAllOutfits().isEmpty());
    }

    @Test
    void testFindAllOutfits() {
        ArrayList<Outfit> outfitList = new ArrayList<>();

        when(this.outfitRepository.findAll()).thenReturn(outfitList);

        List<Outfit> actualFindAllOutfitsResult = this.outfitServiceImpl.findAllOutfits();

        assertSame(outfitList, actualFindAllOutfitsResult);
        assertTrue(actualFindAllOutfitsResult.isEmpty());
        verify(this.outfitRepository).findAll();
    }


//    @Test
//    void testTransformOutfitToJsonObject() {
//        when(this.itemService.createJsonArrayOfItems(any())).thenReturn(new JSONArray());
//
//        Coat coat = new Coat();
//        coat.setCoatCategory(CoatCategory.JACKET);
//        coat.setId(123L);
//        coat.setOutfits(new ArrayList<>());
//
//        Outfit outfit = new Outfit();
//        outfit.setCoat(coat);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(new ArrayList<>());
//        outfit.setNrOfReviews(1);
//        outfit.setNrOfStars(1);
//        outfit.setRating(10.0);
//        assertEquals(7, this.outfitServiceImpl.transformOutfitToJsonObject(outfit).size());
//        verify(this.itemService).createJsonArrayOfItems(any());
//        assertTrue(this.outfitServiceImpl.findAllOutfits().isEmpty());
//    }
//
//    @Test
//    void testTransformOutfitToJsonObject2() {
//        Coat coat = new Coat();
//        coat.setCoatCategory(CoatCategory.JACKET);
//        coat.setId(123L);
//        coat.setOutfits(new ArrayList<>());
//
//        Outfit outfit = new Outfit();
//        outfit.setCoat(coat);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(new ArrayList<>());
//        outfit.setNrOfReviews(1);
//        outfit.setNrOfStars(1);
//        outfit.setRating(10.0);
//        assertEquals(7, this.outfitServiceImpl.transformOutfitToJsonObject(outfit).size());
//    }
//
//    @Test
//    void testTransformOutfitToJsonObject3() {
//        Coat coat = new Coat();
//        coat.setCoatCategory(CoatCategory.JACKET);
//        coat.setId(123L);
//        coat.setOutfits(new ArrayList<>());
//
//        Outfit outfit = new Outfit(123L, "The characteristics of someone or something", new Coat());
//        outfit.setCoat(coat);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(new ArrayList<>());
//        outfit.setNrOfReviews(1);
//        outfit.setNrOfStars(1);
//        outfit.setRating(10.0);
//        assertEquals(7, this.outfitServiceImpl.transformOutfitToJsonObject(outfit).size());
//    }
//
//    @Test
//    void testTransformOutfitToJsonObject4() {
//        Coat coat = new Coat();
//        coat.setCoatCategory(null);
//        coat.setId(123L);
//        coat.setOutfits(new ArrayList<>());
//
//        Outfit outfit = new Outfit();
//        outfit.setCoat(coat);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(new ArrayList<>());
//        outfit.setNrOfReviews(1);
//        outfit.setNrOfStars(1);
//        outfit.setRating(10.0);
//        assertEquals(7, this.outfitServiceImpl.transformOutfitToJsonObject(outfit).size());
//    }
//
//    @Test
//    void testTransformOutfitToJsonObject5() {
//        Coat coat = new Coat();
//        coat.setCoatCategory(CoatCategory.JACKET);
//        coat.setId(123L);
//        coat.setOutfits(new ArrayList<>());
//
//        Outfit outfit = new Outfit();
//        outfit.setCoat(coat);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(new ArrayList<>());
//        outfit.setNrOfReviews(0);
//        outfit.setNrOfStars(0);
//        outfit.setRating(10.0);
//
//        ArrayList<Outfit> outfitList = new ArrayList<>();
//        outfitList.add(outfit);
//
//        Coat coat1 = new Coat();
//        coat1.setCoatCategory(CoatCategory.JACKET);
//        coat1.setId(123L);
//        coat1.setOutfits(outfitList);
//
//        Outfit outfit1 = new Outfit();
//        outfit1.setCoat(coat1);
//        outfit1.setDescription("The characteristics of someone or something");
//        outfit1.setHistories(new ArrayList<>());
//        outfit1.setId(123L);
//        outfit1.setItems(new ArrayList<>());
//        outfit1.setNrOfReviews(1);
//        outfit1.setNrOfStars(1);
//        outfit1.setRating(10.0);
//        assertEquals(7, this.outfitServiceImpl.transformOutfitToJsonObject(outfit1).size());
//    }
//
//    @Test
//    void testTransformOutfitToJsonObject6() {
//        Coat coat = new Coat();
//        coat.setCoatCategory(CoatCategory.JACKET);
//        coat.setId(123L);
//        coat.setOutfits(new ArrayList<>());
//
//        Outfit outfit = new Outfit();
//        outfit.setCoat(coat);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(new ArrayList<>());
//        outfit.setNrOfReviews(0);
//        outfit.setNrOfStars(0);
//        outfit.setRating(10.0);
//
//        Coat coat1 = new Coat();
//        coat1.setCoatCategory(CoatCategory.JACKET);
//        coat1.setId(123L);
//        coat1.setOutfits(new ArrayList<>());
//
//        Outfit outfit1 = new Outfit();
//        outfit1.setCoat(coat1);
//        outfit1.setDescription("The characteristics of someone or something");
//        outfit1.setHistories(new ArrayList<>());
//        outfit1.setId(123L);
//        outfit1.setItems(new ArrayList<>());
//        outfit1.setNrOfReviews(0);
//        outfit1.setNrOfStars(0);
//        outfit1.setRating(10.0);
//
//        ArrayList<Outfit> outfitList = new ArrayList<>();
//        outfitList.add(outfit1);
//        outfitList.add(outfit);
//
//        Coat coat2 = new Coat();
//        coat2.setCoatCategory(CoatCategory.JACKET);
//        coat2.setId(123L);
//        coat2.setOutfits(outfitList);
//
//        Outfit outfit2 = new Outfit();
//        outfit2.setCoat(coat2);
//        outfit2.setDescription("The characteristics of someone or something");
//        outfit2.setHistories(new ArrayList<>());
//        outfit2.setId(123L);
//        outfit2.setItems(new ArrayList<>());
//        outfit2.setNrOfReviews(1);
//        outfit2.setNrOfStars(1);
//        outfit2.setRating(10.0);
//        assertEquals(7, this.outfitServiceImpl.transformOutfitToJsonObject(outfit2).size());
//    }
//
//    @Test
//    void testTransformOutfitToJsonObject7() {
//        Coat coat = new Coat();
//        coat.setCoatCategory(CoatCategory.JACKET);
//        coat.setId(123L);
//        coat.setOutfits(new ArrayList<>());
//
//        User user = new User();
//        user.setAge(0);
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
//        item.setLastWashingDay(LocalDate.ofEpochDay(0L));
//        item.setLastWearing(LocalDate.ofEpochDay(0L));
//        item.setMaterial(Material.WOOL);
//        item.setNrOfWearsSinceLastWash(0);
//        item.setOutfits(new ArrayList<>());
//        item.setSize(Size.M);
//        item.setStyle(Style.CASUAL);
//        //item.setUser(user);
//        item.setWashingZoneColor(WashingZoneColor.COLOR);
//
//        ArrayList<Item> itemList = new ArrayList<>();
//        itemList.add(item);
//
//        Outfit outfit = new Outfit();
//        outfit.setCoat(coat);
//        outfit.setDescription("The characteristics of someone or something");
//        outfit.setHistories(new ArrayList<>());
//        outfit.setId(123L);
//        outfit.setItems(itemList);
//        outfit.setNrOfReviews(1);
//        outfit.setNrOfStars(1);
//        outfit.setRating(10.0);
//        assertEquals(7, this.outfitServiceImpl.transformOutfitToJsonObject(outfit).size());
//    }

    @Test
    void writeOutfitToFile() throws JSONException {

        Outfit outfit = new Outfit();
        outfit.setDescription("outfit1");
        outfit.setId(10L);
        outfit.setItems(new ArrayList<>());
        outfit.setCoat(new Coat());
        outfit.setRating(2.0);
        outfit.setNrOfReviews(5);
        outfit.setNrOfStars(10);

        this.outfitServiceImpl.writeOutfitToFile(outfit);

        JSONArray result = this.outfitServiceImpl.getOutfitsFromFile();

        JSONAssert.assertEquals(this.outfitServiceImpl.transformOutfitToJsonObject(outfit).toJSONString(),
                result.get(result.size() - 1).toString(), false);

    }

    @Test
    void testGetOutfitsFromFile() {
        JSONArray actualOutfitsFromFile = this.outfitServiceImpl.getOutfitsFromFile();
        assertEquals(7, actualOutfitsFromFile.size());
        assertEquals(7, ((Map<Object, Object>) actualOutfitsFromFile.get(0)).size());
        assertEquals(7, ((Map<Object, Object>) actualOutfitsFromFile.get(6)).size());
        assertEquals(7, ((Map<Object, Object>) actualOutfitsFromFile.get(5)).size());
        assertEquals(7, ((Map<Object, Object>) actualOutfitsFromFile.get(4)).size());
        assertEquals(7, ((Map<Object, Object>) actualOutfitsFromFile.get(3)).size());
        assertEquals(7, ((Map<Object, Object>) actualOutfitsFromFile.get(2)).size());
        assertEquals(7, ((Map<Object, Object>) actualOutfitsFromFile.get(1)).size());
    }

}

