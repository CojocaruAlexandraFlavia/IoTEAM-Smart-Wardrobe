package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.repository.OutfitRepository;
import com.example.smartwardrobe.service.OutfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutfitServiceImpl implements OutfitService {

    @Autowired
    private OutfitRepository outfitRepository;

    @Override
    public Outfit saveOutfit(Outfit outfit) {
        return outfitRepository.save(outfit);
    }

    @Override
    public void deleteOutfitById(Long id) {
        outfitRepository.deleteById(id);
    }

    @Override
    public void deleteOutfit(Outfit outfit) {
        outfitRepository.delete(outfit);
    }

    @Override
    public Outfit findOutfitById(Long id) {
        return outfitRepository.findById(id).orElseThrow();
    }

    @Override
    public Outfit findOutfitByItems(List<Item> items) {
        return outfitRepository.findByItems(items);
    }

    @Override
    public List<Outfit> findAllOutfits() {
        return outfitRepository.findAll();
    }
}
