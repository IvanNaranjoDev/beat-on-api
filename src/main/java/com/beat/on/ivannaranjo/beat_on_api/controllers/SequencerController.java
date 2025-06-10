package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.CategoryDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.SoundDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.CategoryService;
import com.beat.on.ivannaranjo.beat_on_api.services.SoundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Obtener todos los sonidos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sonidos obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<SoundDTO>> getSounds() {
        try {
            List<SoundDTO> sounds = soundService.getAllSounds();
            return ResponseEntity.ok(sounds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Obtener todas las categorías de sonidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
