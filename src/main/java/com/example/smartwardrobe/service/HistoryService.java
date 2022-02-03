package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.dto.HistoryDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface HistoryService {

    History saveHistory(History history);
    History convertDtoToEntity(HistoryDto historyDto);

}
