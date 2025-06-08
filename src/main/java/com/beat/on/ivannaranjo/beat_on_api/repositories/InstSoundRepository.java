package com.beat.on.ivannaranjo.beat_on_api.repositories;

import com.beat.on.ivannaranjo.beat_on_api.entities.InstSound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstSoundRepository extends JpaRepository<InstSound, Long> {
    void deleteByInstrumentalId(Long instrumentalId);
    List<InstSound> findByInstrumentalIdOrderByRowIndexAscStepIndexAsc(Long instrumentalId);

}
