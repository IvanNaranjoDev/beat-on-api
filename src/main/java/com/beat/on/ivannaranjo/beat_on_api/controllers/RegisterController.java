package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.UserCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserCreateDTO userCreateDTO, Locale locale) {
        try {
            userCreateDTO.setEnabled(true);
            userCreateDTO.setRoles(null);
            userCreateDTO.setAvatar(null);
            return userService.registerUser(userCreateDTO, locale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario");
        }
    }
}
