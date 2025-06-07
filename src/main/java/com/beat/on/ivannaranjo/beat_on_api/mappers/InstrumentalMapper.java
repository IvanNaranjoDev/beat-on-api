package com.beat.on.ivannaranjo.beat_on_api.mappers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.InstrumentalCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.InstrumentalDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Instrumental;
import com.beat.on.ivannaranjo.beat_on_api.entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class InstrumentalMapper {

    public InstrumentalDTO toDTO(Instrumental instrumental){
        InstrumentalDTO dto = new InstrumentalDTO();

        dto.setId(instrumental.getId());
        dto.setInstName(instrumental.getInstName());
        dto.setBpm(instrumental.getBpm());
        dto.setPublic(instrumental.isPublic());
        dto.setCoverUrl(instrumental.getCoverUrl());
        dto.setCreatedDate(instrumental.getCreatedDate());
        dto.setLastModifiedDate(instrumental.getLastModifiedDate());
        dto.setUserId(instrumental.getUser().getId());

        return dto;
    }

    public Instrumental toEntity(InstrumentalDTO dto, User user){
        Instrumental instrumental = new Instrumental();

        instrumental.setId(dto.getId());
        instrumental.setInstName(dto.getInstName());
        instrumental.setBpm(dto.getBpm());
        instrumental.setPublic(dto.isPublic());
        instrumental.setCoverUrl(dto.getCoverUrl());
        instrumental.setCreatedDate(dto.getCreatedDate());
        instrumental.setLastModifiedDate(dto.getLastModifiedDate());
        instrumental.setUser(user);

        return instrumental;
    }

    public Instrumental toEntity(InstrumentalCreateDTO dto, User user){
        Instrumental instrumental = new Instrumental();

        instrumental.setInstName(dto.getInstName());
        instrumental.setBpm(dto.getBpm());
        instrumental.setPublic(dto.isPublic());
        instrumental.setUser(user);

        return instrumental;
    }
}
