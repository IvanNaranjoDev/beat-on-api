package com.beat.on.ivannaranjo.beat_on_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteAccountRequestDTO {
    private String password;

    public DeleteAccountRequestDTO() {}
}