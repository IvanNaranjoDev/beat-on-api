// En: src/main/java/com/beat/on/ivannaranjo/beat_on_api/controllers/MailController.java
package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.EmailRequestDTO;
import com.beat.on.ivannaranjo.beat_on_api.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mails")
public class MailController {

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Enviar correo simple (texto plano)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo enviado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al enviar correo",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
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

    @Operation(summary = "Enviar correo en formato HTML")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo HTML enviado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al enviar correo",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
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