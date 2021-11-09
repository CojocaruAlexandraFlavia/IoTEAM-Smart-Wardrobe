package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.History;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryService {

    History findHistoryById(Long id);
    History saveHistory(History history);
    History findHistoryByDateTime(LocalDateTime localDateTime);
    void deleteHistoryById(Long id);
    List<History> findAllHistories();

}