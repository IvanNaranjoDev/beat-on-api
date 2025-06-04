// En: src/main/java/com/beat/on/ivannaranjo/beat_on_api/controllers/MailController.java
package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.EmailRequestDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mails")
public class MailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/simple")
    public ResponseEntity<?> sendSimpleEmail(@RequestBody EmailRequestDTO request) {
        try {
            emailService.sendSimpleMessage(request.getTo(), request.getSubject(), request.getText());
            return ResponseEntity.ok("Email enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar correo: " + e.getMessage());
        }
    }

    @PostMapping("/html")
    public ResponseEntity<?> sendHTMLEmail(@RequestBody EmailRequestDTO request) {
        try {
            emailService.sendHtmlMessage(request.getTo(), request.getSubject(), request.getText());
            return ResponseEntity.ok("Email enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar correo: " + e.getMessage());
        }
    }
}