package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.model.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutfitRepository  extends JpaRepository<Outfit, Long> {
}
