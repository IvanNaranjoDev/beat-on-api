package com.beat.on.ivannaranjo.beat_on_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoundDTO {
    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String name;

    @NotEmpty
    @Size(max = 10)
    private String duration;

    @NotEmpty
    @Size(max = 255)
    private String image;

    @NotEmpty
    @Size(max = 255)
    private String soundPath;

    private Boolean enabled;

    private CategoryDTO category;
}
