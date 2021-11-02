package com.example.smartwardrobe.model;

import com.example.smartwardrobe.enums.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue
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

    private LocalDate lastWearing;

    @Enumerated(EnumType.STRING)
    private WashingZoneColor washingZoneColor;

    @ManyToMany(mappedBy = "items")
    private List<Outfit> outfits;

    @ManyToOne
    @JoinColumn(name = "user_id")
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

}
