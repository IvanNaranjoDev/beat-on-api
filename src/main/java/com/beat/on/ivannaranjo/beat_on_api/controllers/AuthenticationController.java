package com.beat.on.ivannaranjo.beat_on_api.controllers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.AuthRequestDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.AuthResponseDTO;
import com.beat.on.ivannaranjo.beat_on_api.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Autenticar usuario", description = "Autentica al usuario usando nombre de usuario y contraseña, y devuelve un token JWT si las credenciales son válidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos faltantes o inválidos",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class)))
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody AuthRequestDTO authRequest){
        try {
            if (authRequest.getUsername() == null || authRequest.getPassword() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(null, "El nombre de usuario y la contraseña son obligatorios."));
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            String username = authentication.getName();

            List<String> roles = authentication.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .toList();
            String token = jwtUtil.generateToken(username, roles);
            return ResponseEntity.ok(new AuthResponseDTO(token, "Authentication successful"));
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDTO(null, "Credenciales inválidas. Por favor verifica tus datos."));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponseDTO(null, "Ocurrio un error inesperado. Por favor inténtalo de nuevo más tarde."));
        }
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponseDTO> handlerException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponseDTO(null, "Ocurrió un error inesperado: " + e.getMessage()));
    }
}