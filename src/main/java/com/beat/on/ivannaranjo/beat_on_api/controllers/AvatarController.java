package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.AvatarCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.AvatarDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.AvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/avatars")
public class AvatarController {
    private static final Logger logger = LoggerFactory.getLogger(AvatarController.class);

    @Autowired
    private AvatarService avatarService;

    // *** OBTENCIÓN DE TODOS LOS AVATARES ***
    @Operation(summary = "Obtención de todos los avatares", description = "Devuelve una lista de todos los avatares disponibles en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de avatares recuperada existosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AvatarDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<AvatarDTO>> getAllAvatars(){
        try{
            List<AvatarDTO> avatars = avatarService.getAllAvatars();
            return ResponseEntity.ok(avatars);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // *** OBTENCIÓN DE DATOS POR ID ***
    @Operation(summary = "Obtención de datos por ID", description = "Recupera un avatar específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar obtenido.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvatarDTO.class))),
            @ApiResponse(responseCode = "404", description = "Avatar no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getAvatarById(@PathVariable Long id) {
        logger.info("Buscando avatar con ID {}", id);
        try {
            Optional<AvatarDTO> avatarDTO = avatarService.getAvatarById(id);
            if (avatarDTO.isPresent()) {
                return ResponseEntity.ok(avatarDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El avatar no existe.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el avatar.");
        }
    }


    // *** CREACIÓN DE DATOS ***
    @Operation(summary = "Creación de un nuevo avayar", description = "Crea un nuevo avatar en la aplicación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avatar creado exitosamente.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvatarDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createAvatar(
            @Valid @ModelAttribute AvatarCreateDTO createDTO,
            Locale locale) {
        try {
            AvatarDTO avatarDTO = avatarService.createAvatar(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(avatarDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el avatar.");
        }
    }

    // *** EDICIÓN DE DATOS ***
    @Operation(summary = "Actualización de un avatar", description = "Actualiza los datos de un avatar existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar actualizado existosamente.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvatarDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateAvatar(
            @PathVariable Long id,
            @Valid @ModelAttribute AvatarCreateDTO updateDTO,
            Locale locale) {

        try {
            AvatarDTO updatedAvatar = avatarService.updateAvatar(id, updateDTO);
            return ResponseEntity.ok(updatedAvatar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el avatar.");
        }
    }

    // *** ELIMINACIÓN DE DATOS ***
    @Operation(summary = "Eliminación de un avatar", description = "Elimina un avatar de la aplicación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Avatar no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAvatar(@PathVariable Long id) {
        try {
            avatarService.deleteAvatar(id);
            return ResponseEntity.ok("Avatar eliminado con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el avatar.");
        }
    }
}
