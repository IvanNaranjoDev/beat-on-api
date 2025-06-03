package com.beat.on.ivannaranjo.beat_on_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileUpdateDTO {

    @NotEmpty
    @Size(max = 50)
    private String username;

    @NotEmpty
    @Size(max = 100)
    private String email;

    private AvatarDTO avatar;
}

