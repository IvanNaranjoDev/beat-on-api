package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.LikeCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.LikeDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Operation(summary = "Obtener todos los likes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de likes obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<LikeDTO>> getAllLikes() {
        try {
            List<LikeDTO> likes = likeService.getAllLikes();
            return ResponseEntity.ok(likes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Obtener un like por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like encontrado"),
            @ApiResponse(responseCode = "404", description = "Like no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getLikeBiId(@PathVariable Long id) {
        try {
            Optional<LikeDTO> likeDTO = likeService.getLikeById(id);
            if (likeDTO.isPresent()) {
                return ResponseEntity.ok(likeDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El like no existe.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el like.");
        }
    }

    @Operation(summary = "Obtener los likes realizados por un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de likes del usuario obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<LikeDTO>> getLikesByUser(@PathVariable Long userId) {
        try {
            List<LikeDTO> likes = likeService.getLikesByUserId(userId);
            return ResponseEntity.ok(likes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Crear un nuevo like")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Like creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear el like"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createLike(@RequestBody LikeCreateDTO dto) {
        try {
            LikeDTO newLike = likeService.createLike(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newLike);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el like.");
        }
    }

    @Operation(summary = "Eliminar un like por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like eliminado con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable Long id) {
        try {
            likeService.deleteLike(id);
            return ResponseEntity.ok("Like eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el like.");
        }
    }
}
