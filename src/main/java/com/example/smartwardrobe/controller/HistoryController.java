package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.dto.HistoryDto;
import com.example.smartwardrobe.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping
    public History saveHistory(@RequestBody HistoryDto historyDto){
        History history = historyService.convertDtoToEntity(historyDto);
        return historyService.saveHistory(history);
    }



}
