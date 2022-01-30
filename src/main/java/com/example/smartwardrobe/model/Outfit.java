package com.example.smartwardrobe.model;

import com.example.smartwardrobe.enums.CoatCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;

//    @Enumerated(EnumType.STRING)
//    private Coat coat;

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





    public Outfit(Long id, String description, Coat coat) {
        this.id = id;
        this.description = description;
        this.coat = coat;
    }

    public Outfit() { }

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

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"description\":\"" + description + '\"' +
                ", \"coat\":" + coat +
                ", \"items\":" + items +
                '}';
    }
}
