package com.example.smartwardrobe.model.dto;

import java.util.List;

public class OutfitDto {

    private String description;

    private CoatDto coat;

    private int nrOfStars;

    private int nrOfReviews;

    private double rating;

    private List<ItemDto> items;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CoatDto getCoat() {
        return coat;
    }

    public void setCoat(CoatDto coatDto) {
        this.coat = coatDto;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> itemDtoList) {
        this.items = itemDtoList;
    }

    public int getNrOfStars() {
        return nrOfStars;
    }

    public void setNrOfStars(int nrOfStars) {
        this.nrOfStars = nrOfStars;
    }

    public int getNrOfReviews() {
        return nrOfReviews;
    }

    public void setNrOfReviews(int nrOfReviews) {
        this.nrOfReviews = nrOfReviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
