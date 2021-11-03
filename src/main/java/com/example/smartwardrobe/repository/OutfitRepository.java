package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.enums.Coat;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OutfitRepository  extends JpaRepository<Outfit, Long> {

//    Outfit save(Outfit outfit);
//    Optional<Outfit> findById(Long id);
//    Optional<Outfit> findByCoat(Coat coat);
//    void deleteById(Long id);
    Outfit findByItems(List<Item> items);

}
