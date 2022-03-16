package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.Outfit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class HistoryRepositoryTest {

    @MockBean
    HistoryRepository historyRepository;

    @Test
    void testFindByDateTimeOK(){
        History history = new History();
        LocalDate localDate = LocalDate.parse("2022-01-31");
        history.setDateTime(localDate);
        history.setOutfit(new Outfit());

        when(historyRepository.findByDateTime(localDate)).thenReturn(java.util.Optional.of(history));

        Optional<History> actualHistory = historyRepository.findByDateTime(localDate);

        assertEquals(localDate, actualHistory.get().getDateTime());
        assertSame(history.getOutfit(), actualHistory.get().getOutfit());

        verify(this.historyRepository).findByDateTime(localDate);
    }

    @Test
    void testFindByDateTimeNotOK(){
        LocalDate localDate = LocalDate.parse("2000-01-31");

        when(historyRepository.findByDateTime(any())).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> historyRepository.findByDateTime(localDate));
    }

}
