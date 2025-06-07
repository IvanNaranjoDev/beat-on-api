package com.beat.on.ivannaranjo.beat_on_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserCreateDTO {
    @NotEmpty
    @Size(max = 50)
    private String username;

    @NotEmpty
    @Size(max = 100)
    private String email;

    @Size(max = 50)
    private String password;

    private Boolean enabled;

    private AvatarDTO avatar;

    private Set<RoleDTO> roles;
}
