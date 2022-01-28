package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Coat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoatRepository extends JpaRepository<Coat, Long> {
    List<Coat> findByCoatCategory(CoatCategory coatCategory);
    List<Coat> findByStyle(Style style);
}
