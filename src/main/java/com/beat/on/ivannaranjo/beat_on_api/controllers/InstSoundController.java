package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.InstSoundCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.InstSoundDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.StepDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.InstSoundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Obtener todas las posiciones de sonidos")
    @ApiResponse(responseCode = "200", description = "Lista de posiciones obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<InstSoundDTO>> getAllInstSounds(){
        try{
            List<InstSoundDTO> instSounds = instSoundService.getAllInstSounds();
            return ResponseEntity.ok(instSounds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Obtener una posición de sonido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posición encontrada"),
            @ApiResponse(responseCode = "404", description = "La posición no existe")
    })
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

    @Operation(summary = "Crear una nueva posición de sonido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Posición creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
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

    @Operation(summary = "Actualizar los pasos de una posición de sonido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pasos actualizados correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
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

    @Operation(summary = "Eliminar una posición de sonido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posición eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "La posición no existe")
    })
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

    @Operation(summary = "Obtener posiciones de sonidos por ID de instrumental")
    @ApiResponse(responseCode = "200", description = "Lista de posiciones obtenida correctamente")
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

    @Operation(summary = "Obtener la estructura del secuenciador por ID de instrumental")
    @ApiResponse(responseCode = "200", description = "Estructura del secuenciador obtenida correctamente")
    @GetMapping("/{instrumentalId}/steps")
    public ResponseEntity<List<StepDTO>> getSequencerSteps(@PathVariable Long instrumentalId) {
        return ResponseEntity.ok(instSoundService.getSequencerRowsByInstrumentalId(instrumentalId));
    }

}
