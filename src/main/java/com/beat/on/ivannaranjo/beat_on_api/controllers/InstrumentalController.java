package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.InstrumentalCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.InstrumentalDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.InstrumentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instrumentals")
public class InstrumentalController {

    @Autowired
    private InstrumentalService instrumentalService;

    @Operation(summary = "Obtener todos los instrumentales", description = "Devuelve una lista de todos los instrumentales registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de instrumentales obtenida correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = InstrumentalDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<InstrumentalDTO>> getAllInstrumentals() {
        try {
            List<InstrumentalDTO> instrumentalsDTO = instrumentalService.getAllInstrumentals();
            return ResponseEntity.ok(instrumentalsDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Obtener un instrumental por ID", description = "Devuelve un instrumental según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instrumental encontrado",
                    content = @Content(schema = @Schema(implementation = InstrumentalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Instrumental no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InstrumentalDTO> getInstrumentalById(@PathVariable Long id) {
        try {
            Optional<InstrumentalDTO> instrumentalDTO = instrumentalService.getInstrumentalById(id);
            return instrumentalDTO.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Crear un nuevo instrumental", description = "Registra un nuevo instrumental con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Instrumental creado exitosamente",
                    content = @Content(schema = @Schema(implementation = InstrumentalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno al crear el instrumental")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createInstrumental(
            @RequestPart("data") @Valid InstrumentalCreateDTO instrumentalCreateDTO,
            @RequestPart(value = "coverUrl", required = false) MultipartFile coverImage) {
        try {
            instrumentalCreateDTO.setCoverUrl(coverImage);
            return instrumentalService.createInstrumental(instrumentalCreateDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el instrumental");
        }
    }

    @Operation(summary = "Actualizar un instrumental", description = "Actualiza los datos de un instrumental existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instrumental actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = InstrumentalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Instrumental no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al actualizar el instrumental")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateInstrumental(
            @PathVariable Long id,
            @RequestPart("data") @Valid InstrumentalCreateDTO instrumentalCreateDTO,
            @RequestPart(value = "coverUrl", required = false) MultipartFile coverImage) {
        try {
            instrumentalCreateDTO.setCoverUrl(coverImage);
            return instrumentalService.updateInstrumental(id, instrumentalCreateDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el instrumental");
        }
    }

    @Operation(summary = "Eliminar un instrumental", description = "Elimina un instrumental existente mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instrumental eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Instrumental no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al eliminar el instrumental")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstrumental(@PathVariable Long id) {
        try {
            return instrumentalService.deleteInstrumental(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el instrumental");
        }
    }
}
