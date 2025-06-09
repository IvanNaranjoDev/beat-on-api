package com.beat.on.ivannaranjo.beat_on_api.repositories;

import com.beat.on.ivannaranjo.beat_on_api.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserId(Long userId);
}
