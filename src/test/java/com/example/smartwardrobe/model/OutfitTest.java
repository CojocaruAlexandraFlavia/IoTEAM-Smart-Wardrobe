package com.example.smartwardrobe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.example.smartwardrobe.enums.CoatCategory;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class OutfitTest {
    @Test
    void testConstructor() {
        Outfit actualOutfit = new Outfit();
        Coat coat = new Coat();
        coat.setCoatCategory(CoatCategory.JACKET);
        coat.setId(123L);
        List<Outfit> outfitList = new ArrayList<>();
        coat.setOutfits(outfitList);
        actualOutfit.setCoat(coat);
        actualOutfit.setDescription("The characteristics of someone or something");
        List<History> historyList = new ArrayList<>();
        actualOutfit.setHistories(historyList);
        actualOutfit.setId(123L);
        List<Item> itemList = new ArrayList<>();
        actualOutfit.setItems(itemList);
        actualOutfit.setNrOfReviews(1);
        actualOutfit.setNrOfStars(1);
        actualOutfit.setRating(10.0);

        assertSame(coat, actualOutfit.getCoat());
        assertEquals("The characteristics of someone or something", actualOutfit.getDescription());

        List<History> histories = actualOutfit.getHistories();

        assertSame(historyList, histories);
        assertEquals(outfitList, histories);
        List<Item> items = actualOutfit.getItems();
        assertEquals(items, histories);
        assertEquals(123L, actualOutfit.getId().longValue());
        assertSame(itemList, items);
        assertEquals(outfitList, items);
        assertEquals(historyList, items);
        assertEquals(1, actualOutfit.getNrOfReviews());
        assertEquals(1, actualOutfit.getNrOfStars());
        assertEquals(10.0, actualOutfit.getRating());
    }

    @Test
    void testAllArgsConstructor(){
        Coat coat = new Coat();
        List<History> historyList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
        Outfit outfit = new Outfit(1L, "description", 5, 1, 5.0, coat,
                historyList, itemList);

        assertEquals(1L, outfit.getId());
        assertEquals("description", outfit.getDescription());
        assertEquals(5, outfit.getNrOfStars());
        assertEquals(1, outfit.getNrOfReviews());
        assertEquals(5.0, outfit.getRating());
        assertSame(coat, outfit.getCoat());
        assertSame(historyList, outfit.getHistories());
        assertSame(itemList, outfit.getItems());
    }
}

