package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.UserCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.UserDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> usersDTO = userService.getAllUsers();
            return ResponseEntity.ok(usersDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable Long id){
        try {
            Optional<UserDTO> userDTO = userService.getUserById(id);
            if(userDTO.isPresent()){
                return ResponseEntity.ok(userDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO, Locale locale) {
        try {
            return  userService.createUser(userCreateDTO, locale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDTO userCreateDTO, Locale locale) {
        try {
            return userService.updateUser(id, userCreateDTO, locale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return userService.deleteUser(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario");
        }
    }
}
