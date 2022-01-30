package com.example.smartwardrobe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoatDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("coatCategory")
    private String coatCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoatCategory() {
        return coatCategory;
    }

    public void setCoatCategory(String coatCategory) {
        this.coatCategory = coatCategory;
    }
}
