package com.beat.on.ivannaranjo.beat_on_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDTO {
    private String to;
    private String subject;
    private String text;
}
