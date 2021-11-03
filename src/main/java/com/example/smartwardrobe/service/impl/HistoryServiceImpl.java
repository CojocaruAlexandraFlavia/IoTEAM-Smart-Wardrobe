package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.repository.HistoryRepository;
import com.example.smartwardrobe.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public History findHistoryById(Long id) {
        return historyRepository.findById(id).orElseThrow();
    }

    @Override
    public History saveHistory(History history) {
        return historyRepository.save(history);
    }

    @Override
    public History findHistoryByDateTime(LocalDateTime localDateTime) {
        return historyRepository.findByDateTime(localDateTime).orElseThrow();
    }

    @Override
    public void deleteHistoryById(Long id) {
        historyRepository.deleteById(id);
    }

    @Override
    public List<History> findAllHistories() {
        return historyRepository.findAll();
    }
}
