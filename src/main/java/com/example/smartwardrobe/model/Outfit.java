package com.example.smartwardrobe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;

    private int nrOfStars;

    private int nrOfReviews;

    private double rating;

    @ManyToOne
    @JoinColumn(name = "coat_id")
    private Coat coat;

    @OneToMany(targetEntity = History.class, mappedBy = "outfit", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<History> histories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "outfit_item",
            joinColumns = @JoinColumn(name = "outfit_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coat getCoat() {
        return coat;
    }

    public void setCoat(Coat coat) {
        this.coat = coat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
