package com.beat.on.ivannaranjo.beat_on_api.repositories;

import com.beat.on.ivannaranjo.beat_on_api.entities.Sound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SoundRepository extends JpaRepository<Sound, Long> {
    Optional<Sound> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
