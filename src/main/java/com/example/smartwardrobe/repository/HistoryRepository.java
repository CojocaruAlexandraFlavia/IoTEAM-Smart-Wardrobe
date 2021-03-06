package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    Optional<History> findByDateTime(LocalDate localDate);
}
