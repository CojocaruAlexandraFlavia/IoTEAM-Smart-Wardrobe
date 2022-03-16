package com.example.smartwardrobe.model.dto;

public class HistoryDto {

    private String datetime;

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

}
