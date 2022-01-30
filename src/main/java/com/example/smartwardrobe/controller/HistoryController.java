package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.dto.HistoryDto;
import com.example.smartwardrobe.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public List<History> getAllHistories() {
        return historyService.findAllHistories();
    }

    @GetMapping("/{id}")
    public History getHistoryById(@PathVariable("id") Long id){
        return historyService.findHistoryById(id);
    }

    @GetMapping("/{date}")
    public History getHistoryByDate(@PathVariable("date") String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return historyService.findHistoryByDateTime(LocalDateTime.parse(date, formatter));
    }

    @PostMapping
    public History saveHistory(@RequestBody HistoryDto historyDto){
        return historyService.saveHistory(historyService.convertDtoToEntity(historyDto));
    }



}
