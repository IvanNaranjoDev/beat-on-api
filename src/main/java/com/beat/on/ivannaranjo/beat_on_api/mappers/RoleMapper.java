package com.beat.on.ivannaranjo.beat_on_api.mappers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.RoleCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.RoleDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleDTO toDTO(Role role){
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());

        return dto;
    }

    public Role toEntity(RoleDTO dto){
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());

        return role;
    }

    public Role toEntity(RoleCreateDTO dto){
        Role role = new Role();
        role.setName(dto.getName());
        return role;
    }
}
