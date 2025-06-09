package com.beat.on.ivannaranjo.beat_on_api.services;

import com.beat.on.ivannaranjo.beat_on_api.dtos.LikeCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.LikeDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Like;
import com.beat.on.ivannaranjo.beat_on_api.mappers.LikeMapper;
import com.beat.on.ivannaranjo.beat_on_api.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeMapper likeMapper;

    public List<LikeDTO> getAllLikes() {
        List<Like> likes = likeRepository.findAll();

        return likes.stream()
                .map(likeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<LikeDTO> getLikeById(Long id) {
        Optional<Like> like = likeRepository.findById(id);
        return like.map(likeMapper::toDTO);
    }

    public LikeDTO createLike(LikeCreateDTO createDTO) {
        Like like = likeMapper.toEntity(createDTO);

        Like savedLike = likeRepository.save(like);
        return likeMapper.toDTO(savedLike);
    }

    public void deleteLike(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Like no encontrado"));

        likeRepository.deleteById(id);
    }

    public List<LikeDTO> getLikesByUserId(Long userId) {
        List<Like> likes = likeRepository.findByUserId(userId);
        return likes.stream()
                .map(likeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
