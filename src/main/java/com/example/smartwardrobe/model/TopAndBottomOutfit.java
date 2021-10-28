package com.example.smartwardrobe.model;

import com.example.smartwardrobe.enums.ItemCategory;

public class TopAndBottomOutfit extends Outfit{

    private Item top;
    private Item bottom;

    public TopAndBottomOutfit(ItemCategory topCategory){
        this.top = new Item();
        top.setItemCategory(topCategory);
        this.bottom = new Item();
        bottom.setItemCategory(ItemCategory.PANTS);
    }

    public Item getTop() {
        return top;
    }

    public void setTop(Item top) {
        this.top = top;
    }

    public Item getBottom() {
        return bottom;
    }

    public void setBottom(Item bottom) {
        this.bottom = bottom;
    }
}
