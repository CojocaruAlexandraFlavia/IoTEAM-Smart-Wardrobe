package com.example.smartwardrobe.model;

import com.example.smartwardrobe.enums.CoatCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Coat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CoatCategory coatCategory;

    @OneToMany(mappedBy = "coat")
    @JsonIgnore
    private List<Outfit> outfits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoatCategory getCoatCategory() {
        return coatCategory;
    }

    public void setCoatCategory(CoatCategory coatCategory) {
        this.coatCategory = coatCategory;
    }

    public List<Outfit> getOutfits() {
        return outfits;
    }

    public void setOutfits(List<Outfit> outfits) {
        this.outfits = outfits;
    }

    @Override
    public String toString() {
        return "Coat{" +
                "id=" + id +
                ", coatCategory=" + coatCategory +
                '}';
    }
}
