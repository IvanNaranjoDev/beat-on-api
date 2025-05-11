package com.beat.on.ivannaranjo.beat_on_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvatarDTO {
    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String path;
}
