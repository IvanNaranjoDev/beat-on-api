package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.InstSoundCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.InstSoundDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.StepDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.InstSoundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/inst-sounds")
public class InstSoundController {

    @Autowired
    private InstSoundService instSoundService;

    @GetMapping
    public ResponseEntity<List<InstSoundDTO>> getAllInstSounds(){
        try{
            List<InstSoundDTO> instSounds = instSoundService.getAllInstSounds();
            return ResponseEntity.ok(instSounds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInstSoundById(@PathVariable Long id) {
        try {
            Optional<InstSoundDTO> instSoundDTO = instSoundService.getInstSoundById(id);
            if (instSoundDTO.isPresent()) {
                return ResponseEntity.ok(instSoundDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La posición no existe.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la posición.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createInstSounds(
            @Valid @RequestBody InstSoundCreateDTO createDTO,
            Locale locale) {
        try {
            InstSoundDTO instSoundDTO = instSoundService.createInstSound(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(instSoundDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la posición.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInstSoundsSteps(
            @PathVariable Long id,
            @Valid @RequestBody List<StepDTO> steps,
            Locale locale) {
        try {
            instSoundService.updateInstSounds(id, steps);
            return ResponseEntity.ok(Collections.singletonMap("message", "Posiciones actualizados correctamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar las posiciones.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstSounds(@PathVariable Long id) {
        try {
            instSoundService.deleteInstSound(id);
            return ResponseEntity.ok("Posición eliminada con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la posición.");
        }
    }

    @GetMapping("/by-instrumental/{instrumentalId}")
    public ResponseEntity<?> getByInstrumentalId(@PathVariable Long instrumentalId) {
        try {
            List<InstSoundDTO> result = instSoundService.getInstSoundsByInstrumentalId(instrumentalId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las posiciones de la instrumental.");
        }
    }

    @GetMapping("/{instrumentalId}/steps")
    public ResponseEntity<List<StepDTO>> getSequencerSteps(@PathVariable Long instrumentalId) {
        return ResponseEntity.ok(instSoundService.getSequencerRowsByInstrumentalId(instrumentalId));
    }

}
