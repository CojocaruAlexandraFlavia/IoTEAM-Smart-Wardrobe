package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.model.Outfit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutfitRepository  extends JpaRepository<Outfit, Long> {
    List<Outfit> findByRatingGreaterThanEqual(double minimRating, Sort sort);
}
