package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository underTest;

    @Test
    void shouldFindItemsByCategory() {

        //given

        ItemCategory category = ItemCategory.SKIRT;
        Item item = new Item();
        item.setItemCategory(category);
        underTest.save(item);

        //when
        List<Item> foundItems = underTest.findByItemCategory(category);

        //then
        Set<ItemCategory> foundCategories =  foundItems.stream().map(Item::getItemCategory).collect(Collectors.toSet());
        Set<ItemCategory> expected = Set.of(category) ;

        assertThat(foundCategories).hasSameElementsAs(expected);
    }

    @Test
    void shouldFindItemsByStyle() {

        //given
        Style style = Style.CASUAL;
        Item item = new Item();
        item.setStyle(style);
        underTest.save(item);

        //when
        List<Item> foundItems = underTest.findByStyle(style);

        //then
        Set<Style> foundStyles =  foundItems.stream().map(Item::getStyle).collect(Collectors.toSet());
        Set<Style> expected = Set.of(style);

        assertThat(foundStyles).hasSameElementsAs(expected);

    }
}