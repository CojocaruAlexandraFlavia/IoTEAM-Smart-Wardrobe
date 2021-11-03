package com.example.smartwardrobe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Entity
public class History {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonSerialize
    @JsonDeserialize
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "outfit_id")
    private Outfit outfit;

    public History(Long id, LocalDateTime dateTime) {
        this.id = id;
        this.dateTime = dateTime;
    }

    public History() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Outfit getOutfit() {
        return outfit;
    }

    public void setOutfit(Outfit outfit) {
        this.outfit = outfit;
    }
}
