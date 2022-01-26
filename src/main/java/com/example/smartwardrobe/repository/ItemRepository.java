package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemCategory(ItemCategory itemCategory);
    List<Item> findByStyle(Style style);

    @Query("SELECT i FROM Item i WHERE i.nrOfWearsSinceLastWash >= 3")
    List<Item> findItemIfDirty();
}
