package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.dto.HistoryDto;
import com.example.smartwardrobe.repository.HistoryRepository;
import com.example.smartwardrobe.service.HistoryService;
import com.example.smartwardrobe.service.OutfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    OutfitService outfitService;

    @Override
    public History saveHistory(History history) {
        return historyRepository.save(history);
    }

    @Override
    public History convertDtoToEntity(HistoryDto historyDto) {
        History history = new History();
        history.setDateTime(LocalDate.parse(historyDto.getDatetime()));
        history.setOutfit(outfitService.findOutfitById(historyDto.getOutfitId()));
        return history;
    }
}
