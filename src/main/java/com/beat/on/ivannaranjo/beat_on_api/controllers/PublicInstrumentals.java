package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.InstrumentalDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.InstrumentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/public-instrumentals")
public class PublicInstrumentals {

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
}
