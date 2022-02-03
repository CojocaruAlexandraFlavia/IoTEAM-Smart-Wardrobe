package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.dto.HistoryDto;
import com.example.smartwardrobe.repository.HistoryRepository;
import com.example.smartwardrobe.service.OutfitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {HistoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
class HistoryServiceImplTest {

    @MockBean
    private HistoryRepository historyRepository;

    @MockBean
    private OutfitService outfitService;

    @Autowired
    private HistoryServiceImpl historyService;

    @Test
    void testSaveHistory(){
        History history = new History();
        history.setDateTime(LocalDate.now());
        history.setId(1L);
        Outfit outfit = new Outfit();
        history.setOutfit(outfit);
        when(this.historyRepository.save(any())).thenReturn(history);

        History result = historyService.saveHistory(history);

        assertThat(result).usingRecursiveComparison().isEqualTo(history);
    }

    @Test
    void testConvertDtoToEntity(){
        HistoryDto historyDto = new HistoryDto();
        historyDto.setDatetime("2022-01-30");
        historyDto.setOutfitId(1L);
        Outfit outfit = new Outfit();
        outfit.setId(1L);

        when(this.outfitService.findOutfitById(any())).thenReturn(outfit);

        History history = new History();
        history.setDateTime(LocalDate.parse(historyDto.getDatetime()));
        history.setOutfit(outfitService.findOutfitById(historyDto.getOutfitId()));

        History result = historyService.convertDtoToEntity(historyDto);

        assertThat(result).usingRecursiveComparison().isEqualTo(history);
    }

}
