package com.beat.on.ivannaranjo.beat_on_api.mappers;


import com.beat.on.ivannaranjo.beat_on_api.dtos.UserCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.UserDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();
        AvatarMapper avatarMapper = new AvatarMapper();
        RoleMapper roleMapper = new RoleMapper();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setEnabled(user.isEnabled());
        dto.setAvatar(avatarMapper.toDTO(user.getAvatar()));
        dto.setRoles(
                user.getRoles().stream()
                        .map(roleMapper::toDTO)
                        .collect(Collectors.toSet())
        );

        return dto;
    }

    public User toEntity(UserDTO dto){
        User user = new User();
        AvatarMapper avatarMapper = new AvatarMapper();
        RoleMapper roleMapper = new RoleMapper();

        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setEnabled(dto.getEnabled());
        user.setAvatar(avatarMapper.toEntity(dto.getAvatar()));
        user.setRoles(
                dto.getRoles().stream()
                        .map(roleMapper::toEntity)
                        .collect(Collectors.toSet())
        );

        return user;
    }

    public User toEntity(UserCreateDTO dto){
        User user = new User();
        AvatarMapper avatarMapper = new AvatarMapper();
        RoleMapper roleMapper = new RoleMapper();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setEnabled(dto.getEnabled());
        user.setAvatar(avatarMapper.toEntity(dto.getAvatar()));
        user.setRoles(
                dto.getRoles().stream()
                        .map(roleMapper::toEntity)
                        .collect(Collectors.toSet())
        );

        return user;
    }
}
