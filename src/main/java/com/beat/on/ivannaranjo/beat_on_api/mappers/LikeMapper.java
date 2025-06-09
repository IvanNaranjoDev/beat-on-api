package com.beat.on.ivannaranjo.beat_on_api.mappers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.LikeCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.LikeDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Instrumental;
import com.beat.on.ivannaranjo.beat_on_api.entities.Like;
import com.beat.on.ivannaranjo.beat_on_api.entities.User;
import com.beat.on.ivannaranjo.beat_on_api.repositories.InstrumentalRepository;
import com.beat.on.ivannaranjo.beat_on_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected InstrumentalRepository instrumentalRepository;

    public LikeDTO toDTO (Like like) {
        LikeDTO dto = new LikeDTO();
        dto.setId(like.getId());
        dto.setUserId(like.getUser().getId());
        dto.setInstrumentalId(like.getInstrumental().getId());

        return dto;
    }

    public Like toEntity (LikeDTO likeDTO) {
        Like like = new Like();
        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Instrumental instrumental = instrumentalRepository.findById(likeDTO.getInstrumentalId())
                .orElseThrow(() -> new RuntimeException("Instrumental no encontrada"));

        like.setId(likeDTO.getId());
        like.setUser(user);
        like.setInstrumental(instrumental);

        return like;
    }

    public Like toEntity(LikeCreateDTO likeDTO){
        Like like = new Like();
        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Instrumental instrumental = instrumentalRepository.findById(likeDTO.getInstrumentalId())
                .orElseThrow(() -> new RuntimeException("Instrumental no encontrada"));

        like.setUser(user);
        like.setInstrumental(instrumental);

        return like;
    }
}
