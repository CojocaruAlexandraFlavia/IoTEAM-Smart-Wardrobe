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
import com.example.smartwardrobe.service.CoatService;
import com.example.smartwardrobe.service.HistoryService;
import com.example.smartwardrobe.service.ItemService;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.smartwardrobe.service.UserService;
import org.json.JSONException;
import org.json.simple.JSONArray;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @MockBean
    private CoatService coatService;

    @MockBean
    private UserService userService;

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
//    void writeOutfitToFile() throws JSONException {
//
//        Outfit outfit = new Outfit();
//        outfit.setDescription("outfit1");
//        outfit.setId(10L);
//        outfit.setItems(new ArrayList<>());
//
//        Coat coat = new Coat();
//        coat.setWashingZoneColor(WashingZoneColor.COLOR);
//        coat.setLastWearing(LocalDate.now());
//        coat.setStyle(Style.OFFICE);
//        coat.setNrOfWearsSinceLastWash(0);
//        coat.setLastWashingDay(LocalDate.now());
//        coat.setSize(Size.S);
//        coat.setMaterial(Material.CASHMERE);
//        coat.setId(123L);
//        coat.setCode("123");
//        coat.setItemColor(ItemColor.RED);
//        coat.setCoatCategory(CoatCategory.JACKET);
//        outfit.setCoat(coat);
//        outfit.setRating(2.0);
//        outfit.setNrOfReviews(5);
//        outfit.setNrOfStars(10);
//
//        this.outfitServiceImpl.writeOutfitToFile(outfit);
//
//        JSONArray result = this.outfitServiceImpl.getOutfitsFromFile();
//
//        JSONAssert.assertEquals(this.outfitServiceImpl.transformOutfitToJsonObject(outfit).toJSONString(),
//                result.get(result.size() - 1).toString(), false);
//
//    }

    @Test
    void testGetOutfitsFromFile() {
        JSONArray actualOutfitsFromFile = this.outfitServiceImpl.getOutfitsFromFile();
        assertEquals(7, actualOutfitsFromFile.size());
        assertEquals(4, ((Map<Object, Object>) actualOutfitsFromFile.get(0)).size());
        assertEquals(4, ((Map<Object, Object>) actualOutfitsFromFile.get(6)).size());
        assertEquals(4, ((Map<Object, Object>) actualOutfitsFromFile.get(5)).size());
        assertEquals(4, ((Map<Object, Object>) actualOutfitsFromFile.get(4)).size());
        assertEquals(4, ((Map<Object, Object>) actualOutfitsFromFile.get(3)).size());
        assertEquals(4, ((Map<Object, Object>) actualOutfitsFromFile.get(2)).size());
        assertEquals(4, ((Map<Object, Object>) actualOutfitsFromFile.get(1)).size());
    }

}

