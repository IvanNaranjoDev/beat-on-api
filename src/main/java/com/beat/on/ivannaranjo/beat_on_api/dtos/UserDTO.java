package com.beat.on.ivannaranjo.beat_on_api.dtos;

import com.beat.on.ivannaranjo.beat_on_api.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean enabled;
    private AvatarDTO avatar;
    private Set<RoleDTO> roles;
}
