package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.AvatarCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.AvatarDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.AvatarService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping
    public ResponseEntity<List<AvatarDTO>> getAllAvatars(){
        try{
            List<AvatarDTO> categories = avatarService.getAllAvatars();
            return ResponseEntity.ok(categories);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated? " + auth.isAuthenticated());
        System.out.println("Authorities: " + auth.getAuthorities());
        System.out.println("Username: " + auth.getName());
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAvatarById(@PathVariable Long id) {
        logger.info("Buscando avatar con ID {}", id);
        try {
            Optional<AvatarDTO> categoryDTO = avatarService.getAvatarById(id);
            if (categoryDTO.isPresent()) {
                return ResponseEntity.ok(categoryDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El avatar no existe.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el.");
        }
    }

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
