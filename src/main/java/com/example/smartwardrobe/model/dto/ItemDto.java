package com.example.smartwardrobe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class ItemDto {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @JsonProperty("id")
    private long id;

    @JsonProperty("material")
    private String material;

    @JsonProperty("size")
    private String size;

    @JsonProperty("code")
    private String code;

    @JsonProperty("itemColor")
    private String itemColor;

    @JsonProperty("style")
    private String style;

    @JsonProperty("itemCategory")
    private String itemCategory;

    @JsonProperty("lastWearing")
    private String lastWearing;

    @JsonProperty("nrOfWearsSinceLastWash")
    private int nrOfWearsSinceLastWash;

    @JsonProperty("washingZoneColor")
    private String washingZOneColor;

    @JsonProperty("lastWashingDay")
    private String lastWashingDay;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getLastWearing() {
        return lastWearing;
    }

    public void setLastWearing(String lastWearing) {
        this.lastWearing = lastWearing;
    }

    public int getNrOfWearsSinceLastWash() {
        return nrOfWearsSinceLastWash;
    }

    public void setNrOfWearsSinceLastWash(int nrOfWearsSinceLastWash) {
        this.nrOfWearsSinceLastWash = nrOfWearsSinceLastWash;
    }

    public String getWashingZOneColor() {
        return washingZOneColor;
    }

    public void setWashingZOneColor(String washingZOneColor) {
        this.washingZOneColor = washingZOneColor;
    }

    public LocalDate getLastWearingDate(){
        return LocalDate.parse(lastWearing, dateFormatter);
    }

    public LocalDate getLastWashingDayDate(){
        return LocalDate.parse(lastWashingDay, dateFormatter);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastWashingDay() {
        return lastWashingDay;
    }

    public void setLastWashingDay(String lastWashingDay) {
        this.lastWashingDay = lastWashingDay;
    }
}
