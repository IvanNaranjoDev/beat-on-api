package com.beat.on.ivannaranjo.beat_on_api.dtos;

import lombok.*;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String message;
}
