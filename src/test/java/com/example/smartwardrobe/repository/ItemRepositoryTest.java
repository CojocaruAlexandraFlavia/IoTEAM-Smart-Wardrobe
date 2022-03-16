package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {

    @MockBean
    ItemRepository itemRepository;

    @Test
    void testFindByItemCategory(){
        List<Item> itemList = new ArrayList<>();
        when(itemRepository.findByItemCategory(any())).thenReturn(itemList);
        List<Item> actualList = itemRepository.findByItemCategory(ItemCategory.DRESS);

        assertSame(itemList, actualList);
        assertTrue(actualList.isEmpty());
        verify(this.itemRepository).findByItemCategory(any());
    }

    @Test
    void testFindByStyle(){
        List<Item> itemList = new ArrayList<>();
        when(itemRepository.findByStyle(any())).thenReturn(itemList);
        List<Item> actualList = itemRepository.findByStyle(Style.CASUAL);

        assertSame(itemList, actualList);
        assertTrue(actualList.isEmpty());
        verify(this.itemRepository).findByStyle(any());
    }

    @Test
    void testFindItemIfDirty(){
        List<Item> itemList = new ArrayList<>();
        when(itemRepository.findItemIfDirty()).thenReturn(itemList);
        List<Item> actualList = itemRepository.findItemIfDirty();

        assertSame(itemList, actualList);
        assertTrue(actualList.isEmpty());
        verify(this.itemRepository).findItemIfDirty();
    }

}
