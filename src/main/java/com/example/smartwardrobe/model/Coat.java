package com.example.smartwardrobe.model;

import com.example.smartwardrobe.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Coat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Material material;

    @Enumerated(EnumType.STRING)
    private Size size;

    private String code;

    @Enumerated(EnumType.STRING)
    private ItemColor itemColor;

    @Enumerated(EnumType.STRING)
    private Style style;

    @Enumerated(EnumType.STRING)
    private CoatCategory coatCategory;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate lastWearing;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate lastWashingDay;

    private int nrOfWearsSinceLastWash;

    @Enumerated(EnumType.STRING)
    private WashingZoneColor washingZoneColor;

    @OneToMany(mappedBy = "coat", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Outfit> outfits;


    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ItemColor getItemColor() {
        return itemColor;
    }

    public void setItemColor(ItemColor itemColor) {
        this.itemColor = itemColor;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public CoatCategory getCoatCategory() {
        return coatCategory;
    }

    public void setCoatCategory(CoatCategory coatCategory) {
        this.coatCategory = coatCategory;
    }

    public LocalDate getLastWearing() {
        return lastWearing;
    }

    public void setLastWearing(LocalDate lastWearing) {
        this.lastWearing = lastWearing;
    }

    public LocalDate getLastWashingDay() {
        return lastWashingDay;
    }

    public void setLastWashingDay(LocalDate lastWashingDay) {
        this.lastWashingDay = lastWashingDay;
    }

    public int getNrOfWearsSinceLastWash() {
        return nrOfWearsSinceLastWash;
    }

    public void setNrOfWearsSinceLastWash(int nrOfWearsSinceLastWash) {
        this.nrOfWearsSinceLastWash = nrOfWearsSinceLastWash;
    }

    public WashingZoneColor getWashingZoneColor() {
        return washingZoneColor;
    }

    public Coat() {
    }

    public Coat(Long id, Material material, Size size, String code, ItemColor itemColor, Style style, CoatCategory coatCategory, LocalDate lastWearing, LocalDate lastWashingDay, int nrOfWearsSinceLastWash, WashingZoneColor washingZoneColor, List<Outfit> outfits) {
        this.id = id;
        this.material = material;
        this.size = size;
        this.code = code;
        this.itemColor = itemColor;
        this.style = style;
        this.coatCategory = coatCategory;
        this.lastWearing = lastWearing;
        this.lastWashingDay = lastWashingDay;
        this.nrOfWearsSinceLastWash = nrOfWearsSinceLastWash;
        this.washingZoneColor = washingZoneColor;
        this.outfits = outfits;
    }

    public void setWashingZoneColor(WashingZoneColor washingZoneColor) {
        this.washingZoneColor = washingZoneColor;
    }

    public List<Outfit> getOutfits() {
        return outfits;
    }

    public void setOutfits(List<Outfit> outfits) {
        this.outfits = outfits;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"material\":\"" + material.toString()+"\"" +
                ", \"size\":\"" + size + "\""+
                ", \"code\":\"" + code + "\"" +
                ", \"itemColor\":\"" + itemColor.toString() +"\""+
                ", \"style\":\"" + style.toString() +"\""+
                ", \"coatCategory\":\"" + coatCategory.toString() +"\""+
                ", \"lastWearing\":" + lastWearing.toString() +
                ", \"lastWashingDay\":" + lastWashingDay.toString() +
                ", \"nrOfWearsSinceLastWash\":" + nrOfWearsSinceLastWash +
                ", \"washingZoneColor\":\"" + washingZoneColor.toString() +"\""+
                '}';
    }
}
