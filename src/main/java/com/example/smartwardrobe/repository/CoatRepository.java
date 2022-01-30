package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.model.Coat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoatRepository extends JpaRepository<Coat, Long> {
}
