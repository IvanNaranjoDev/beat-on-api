package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.CategoryDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.SoundDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.CategoryService;
import com.beat.on.ivannaranjo.beat_on_api.services.SoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sequencer")
public class SequencerController {

    @Autowired
    private SoundService soundService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<SoundDTO>> getSounds() {
        try {
            List<SoundDTO> sounds = soundService.getAllSounds();
            return ResponseEntity.ok(sounds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        try {
            List<CategoryDTO> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
