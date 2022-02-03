package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Coat;
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
class CoatRepositoryTest {

    @MockBean
    CoatRepository coatRepository;

    @Test
    void testFindByCoatCategory(){
        List<Coat> coatList = new ArrayList<>();
        when(coatRepository.findByCoatCategory(any())).thenReturn(coatList);

        List<Coat> result = coatRepository.findByCoatCategory(CoatCategory.JACKET);
        assertSame(coatList, result);
        assertTrue(result.isEmpty());
        verify(this.coatRepository).findByCoatCategory(any());
    }

    @Test
    void testFindByStyle(){
        List<Coat> coatList = new ArrayList<>();
        when(coatRepository.findByStyle(any())).thenReturn(coatList);

        List<Coat> result = coatRepository.findByStyle(Style.OFFICE);
        assertSame(coatList, result);
        assertTrue(result.isEmpty());
        verify(this.coatRepository).findByStyle(any());
    }

}
