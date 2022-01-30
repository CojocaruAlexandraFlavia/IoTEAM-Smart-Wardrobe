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
//@JsonIgnoreProperties(value= {"outfits"})
public class Item {
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
    private ItemCategory itemCategory;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate lastWearing;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate lastWashingDay;

    private int nrOfWearsSinceLastWash;

    @Enumerated(EnumType.STRING)
    private WashingZoneColor washingZoneColor;

    @ManyToMany(mappedBy = "items")
    @JsonIgnore
    private List<Outfit> outfits;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Item(Long id, Material material, Size size, String code, ItemColor itemColor, Style style, ItemCategory itemCategory, LocalDate lastWearing, WashingZoneColor washingZoneColor, List<Outfit> outfits, User user) {
        this.id = id;
        this.material = material;
        this.size = size;
        this.code = code;
        this.itemColor = itemColor;
        this.style = style;
        this.itemCategory = itemCategory;
        this.lastWearing = lastWearing;
        this.washingZoneColor = washingZoneColor;
        this.outfits = outfits;
        this.user = user;
    }

    public Item() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public LocalDate getLastWearing() {
        return lastWearing;
    }

    public void setLastWearing(LocalDate lastWearing) {
        this.lastWearing = lastWearing;
    }

    public WashingZoneColor getWashingZoneColor() {
        return washingZoneColor;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setNrOfWearsSinceLastWash(int nrOfWearsSinceLastWashing) {
        this.nrOfWearsSinceLastWash = nrOfWearsSinceLastWashing;
    }

    @Override
    public String toString() {
        return "{" +
                " \"id\":" + id +
                ", \"material\":\"" + material.toString() +"\""+
                ", \"size\":\"" + size + "\""+
                ", \"code\":\"" + code + "\"" +
                ", \"itemColor\":\"" + itemColor.toString() +"\""+
                ", \"style\":\"" + style.toString() +"\""+
                ", \"itemCategory\":\"" + itemCategory.toString()+"\""+
//                ", \"lastWearing\":" + lastWearing +
//                ", \"lastWashingDay\":" + lastWashingDay +
//                ", \"nrOfWearsSinceLastWash\":" + nrOfWearsSinceLastWash +
                ", \"washingZoneColor\":\"" + washingZoneColor.toString() +"\""+
                '}';
    }
}