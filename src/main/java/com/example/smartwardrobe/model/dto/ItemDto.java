package com.example.smartwardrobe.model.dto;

public class ItemDto {

    private long id;

    private String material;

    private String size;

    private String code;

    private String itemColor;

    private String style;

    private String itemCategory;

    private String lastWearing;

    private int nrOfWearsSinceLastWash;

    private String washingZoneColor;

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

    public String getWashingZoneColor() {
        return washingZoneColor;
    }

    public void setWashingZoneColor(String washingZoneColor) {
        this.washingZoneColor = washingZoneColor;
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
