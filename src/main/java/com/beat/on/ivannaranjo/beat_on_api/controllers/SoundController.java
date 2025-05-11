package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.*;
import com.beat.on.ivannaranjo.beat_on_api.services.SoundService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/sounds")
public class SoundController {

    @Autowired
    private SoundService soundService;

    // *** OBTENCIÓN DE TODOS LOS SONIDOS ***
    @Operation(summary = "Obtención de todos los sonidos", description = "Devuelve una lista de todos los sonidos disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sonidos obtenida exitosamente.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SoundDTO.class)))),
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

    // *** OBTENCIÓN DE SONIDO POR ID ***
    @Operation(summary = "Obtención de un sonido por ID", description = "Devuelve un sonido específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sonido encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SoundDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sonido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getSoundById(@PathVariable Long id) {
        try {
            Optional<SoundDTO> soundDTO = soundService.getSoundById(id);
            if (soundDTO.isPresent()) {
                return ResponseEntity.ok(soundDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El sonido no existe.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el sonido.");
        }
    }

    // *** CREACIÓN DE SONIDO ***
    @Operation(summary = "Creación de un nuevo sonido", description = "Crea un nuevo sonido con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sonido creado exitosamente.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SoundDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o mal formateados"),
            @ApiResponse(responseCode = "500", description = "Error al guardar el sonido o imagen")
    })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createSound(
            @RequestParam("name") String name,
            @RequestParam("duration") String duration,
            @RequestParam("image") MultipartFile image,
            @RequestParam("soundPath") MultipartFile soundPath,
            @RequestParam("enabled") boolean enabled,
            @RequestParam("category") String categoryJson,
            Locale locale) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CategoryDTO category = objectMapper.readValue(categoryJson, CategoryDTO.class);

            SoundCreateDTO createDTO = new SoundCreateDTO();
            createDTO.setName(name);
            createDTO.setDuration(duration);
            createDTO.setImage(image);
            createDTO.setSoundPath(soundPath);
            createDTO.setEnabled(enabled);
            createDTO.setCategory(category);

            SoundDTO soundDTO = soundService.createSound(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(soundDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen o Sonido.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el sonido.");
        }
    }

    // *** ACTUALIZACIÓN DE SONIDO ***
    @Operation(summary = "Actualización de un sonido", description = "Actualiza un sonido existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sonido actualizado exitosamente.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SoundDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o mal formateados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateSound(
            @PathVariable Long id,
            @Valid @ModelAttribute SoundCreateDTO updateDTO,
            Locale locale) {
        try {
            SoundDTO updatedSound = soundService.updateSound(id, updateDTO);
            return ResponseEntity.ok(updatedSound);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen o sonido.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el sonido.");
        }
    }

    // *** ELIMINACIÓN DE SONIDO ***
    @Operation(summary = "Eliminación de un sonido", description = "Elimina un sonido existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sonido eliminado exitosamente."),
            @ApiResponse(responseCode = "404", description = "Sonido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSound(@PathVariable Long id) {
        try {
            soundService.deleteSound(id);
            return ResponseEntity.ok("Sonido eliminado con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el sonido.");
        }
    }
}