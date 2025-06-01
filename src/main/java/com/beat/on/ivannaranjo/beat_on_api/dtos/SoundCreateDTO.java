package com.beat.on.ivannaranjo.beat_on_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SoundCreateDTO {
    @NotEmpty
    @Size(max = 50)
    private String name;

    @NotEmpty
    @Size(max = 10)
    private String duration;

    private MultipartFile image;
    private MultipartFile soundPath;

    private Boolean enabled;

    private Long categoryId;
}
