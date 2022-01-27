package com.example.smartwardrobe.service;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.repository.ItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @MockBean
    private ItemRepository repository;

    @InjectMocks
    private ItemService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveItem() {

        Item item = new Item();
        item.setItemColor(ItemColor.RED);
        item.setStyle(Style.OFFICE);
        item.setCode("c122");

        service.saveItem(item);

        verify(repository, times(1)).save(item);

        //assertThat(savedItem.getId()).isGreaterThan(0);
    }

    @Test
    void findItemById() {

        Long id = 1L;

        when(repository.findById(1L)).thenReturn(Optional.of(new Item(id,
                Material.CASHMERE, Size.L, "10", ItemColor.RED, Style.OFFICE, ItemCategory.DRESS,
                LocalDate.now(), WashingZoneColor.RED, new ArrayList<>(), new User())));

        Item foundItem = service.findItemById(id);

        assertThat(Material.CASHMERE).isEqualTo(foundItem.getMaterial());
        assertThat(Size.L).isEqualTo(foundItem.getSize());
        assertThat("10").isEqualTo(foundItem.getCode());
        assertThat(ItemColor.RED).isEqualTo(foundItem.getItemColor());
        assertThat(Style.OFFICE).isEqualTo(foundItem.getStyle());
        assertThat(ItemCategory.DRESS).isEqualTo(foundItem.getItemCategory());
        assertThat(WashingZoneColor.RED).isEqualTo(foundItem.getWashingZoneColor());


    }

//    @Test
//    void findItemsByCategory() {
//
//        ItemCategory category = ItemCategory.SKIRT;
//        List<Item> foundItems = service.findItemsByCategory(category);
//
//        Set<ItemCategory> foundCategories = foundItems.stream().map(Item::getItemCategory).collect(Collectors.toSet());
//        Set<ItemCategory> expected = Set.of(category);
//
//        assertThat(foundCategories).hasSameElementsAs(expected);
//
//    }

//    @Test
//    void deleteItemById() {
//
//
//        Long savedItemId = service.saveItem(new Item()).getId();
//        service.deleteItemById(savedItemId);
//        List<Item> newItemList = service.findAllItems();
//        assertThat(newItemList).hasSameElementsAs(allItems);
//    }

//    @Test
//    void shouldGetItemsByStyle() {
//
//        Style style = Style.CASUAL;
//        List<Item> foundItems = service.getItemsByStyleName(style.name());
//
//        Set<Style> foundStyles = foundItems.stream().map(Item::getStyle).collect(Collectors.toSet());
//        Set<Style> expected = Set.of(style);
//
//        assertThat(foundStyles).hasSameElementsAs(expected);
//
//    }

    @Test
    void shouldWashItem() {

        //Long itemId = allItems.get(0).getId();


    }

    @Test
    void shouldUpdateItemAfterAddingOutfit() {
    }

    @Test
    void shouldCreateJsonArrayOfItems() {
    }

    @Test
    void shouldGetALlItems(){



    }
}