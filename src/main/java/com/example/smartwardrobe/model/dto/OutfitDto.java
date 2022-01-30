package com.example.smartwardrobe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OutfitDto {

    @JsonProperty("description")
    private String description;

    @JsonProperty("coat")
    private CoatDto coatDto;

    @JsonProperty("items")
    private List<ItemDto> itemDtoList;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CoatDto getCoatDto() {
        return coatDto;
    }

    public void setCoatDto(CoatDto coatDto) {
        this.coatDto = coatDto;
    }

    public List<ItemDto> getItemDtoList() {
        return itemDtoList;
    }

    public void setItemDtoList(List<ItemDto> itemDtoList) {
        this.itemDtoList = itemDtoList;
    }
}
