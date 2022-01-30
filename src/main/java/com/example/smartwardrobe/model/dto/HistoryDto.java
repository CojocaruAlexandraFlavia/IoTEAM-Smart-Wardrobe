package com.example.smartwardrobe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class HistoryDto {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("outfitId")
    private long outfitId;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getOutfitId() {
        return outfitId;
    }

    public void setOutfitId(long outfitId) {
        this.outfitId = outfitId;
    }

    public LocalDate getLocalDateTime(){
        return LocalDate.parse(datetime, dateTimeFormatter);
    }
}
