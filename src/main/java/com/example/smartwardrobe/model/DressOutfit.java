package com.example.smartwardrobe.model;

import com.example.smartwardrobe.enums.ItemCategory;

public class DressOutfit extends Outfit{

    private Item dress;

    public Item getDress() {
        return dress;
    }

    public void setDress(Item dress) {
        this.dress = dress;
    }

    public DressOutfit(){
        this.dress = new Item();
        this.dress.setItemCategory(ItemCategory.DRESS);
    }
}
