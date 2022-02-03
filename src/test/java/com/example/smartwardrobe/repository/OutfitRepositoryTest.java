package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.model.Outfit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class OutfitRepositoryTest {

    @MockBean
    OutfitRepository outfitRepository;

    @Test
    void testFindByRatingGreaterThanEqual(){

        Outfit outfit1 = new Outfit();
        outfit1.setRating(3.5);
        Outfit outfit2 = new Outfit();
        outfit2.setRating(4.5);
        Outfit outfit3 = new Outfit();
        outfit3.setRating(4.25);
        List<Outfit> outfits = Arrays.asList(outfit1, outfit2, outfit3);
        List<Outfit> expectedList = Arrays.asList(outfit2, outfit3);
        System.out.println(expectedList.get(0));
        System.out.println(expectedList.get(1));

        when(outfitRepository.findByRatingGreaterThanEqual(anyDouble(),  any())).thenReturn(expectedList);

        List<Outfit> actualList = outfitRepository.findByRatingGreaterThanEqual(4.0, Sort.by(Sort.Direction.DESC, "rating"));

        assertNotSame(outfits, actualList);
        assertSame(expectedList, actualList);

        verify(this.outfitRepository).findByRatingGreaterThanEqual(anyDouble(), any());

    }

}
