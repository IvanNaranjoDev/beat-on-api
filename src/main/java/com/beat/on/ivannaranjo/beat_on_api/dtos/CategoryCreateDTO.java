package com.beat.on.ivannaranjo.beat_on_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CategoryCreateDTO {

    @NotEmpty
    @Size(max = 50)
    private String name;

    @NotEmpty
    @Size(max = 7)
    private String color;

    private MultipartFile iconUrl;

}
