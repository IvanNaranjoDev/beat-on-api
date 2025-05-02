package com.beat.on.ivannaranjo.beat_on_api.mappers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.AvatarCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.AvatarDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Avatar;
import org.springframework.stereotype.Component;

@Component
public class AvatarMapper {
    public AvatarDTO toDTO(Avatar avatar){
        AvatarDTO dto = new AvatarDTO();
        dto.setId(avatar.getId());
        dto.setPath(avatar.getPathUrl());

        return dto;
    }

    public Avatar toEntity(AvatarDTO dto){
        Avatar avatar = new Avatar();
        avatar.setId(dto.getId());
        avatar.setPathUrl(dto.getPath());

        return avatar;
    }

    public Avatar toEntity(AvatarCreateDTO dto){
        Avatar avatar = new Avatar();
        return avatar;
    }
}
