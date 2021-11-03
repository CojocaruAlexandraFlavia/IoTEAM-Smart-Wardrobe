package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    Optional<History> findByDateTime(LocalDateTime localDateTime);
}
