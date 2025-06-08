package com.beat.on.ivannaranjo.beat_on_api.mappers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.InstSoundCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.InstSoundDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.InstSound;
import com.beat.on.ivannaranjo.beat_on_api.entities.Instrumental;
import com.beat.on.ivannaranjo.beat_on_api.entities.Sound;
import org.springframework.stereotype.Component;

@Component
public class InstSoundMapper {
    public InstSoundDTO toDto(InstSound instSound){
        InstSoundDTO dto = new InstSoundDTO();

        dto.setId(instSound.getId());
        dto.setSoundId(instSound.getSound().getId());
        dto.setInstrumentalId(instSound.getInstrumental().getId());
        dto.setStepIndex(instSound.getStepIndex());
        dto.setRowIndex(instSound.getRowIndex());

        return dto;
    }

    public InstSound toEntity(InstSoundDTO dto) {
        InstSound instSound = new InstSound();

        Sound sound = new Sound();
        sound.setId(dto.getSoundId());
        instSound.setSound(sound);

        Instrumental instrumental = new Instrumental();
        instrumental.setId(dto.getInstrumentalId());
        instSound.setInstrumental(instrumental);

        instSound.setStepIndex(dto.getStepIndex());
        instSound.setRowIndex(dto.getRowIndex());

        return instSound;
    }

    public InstSound toEntity(InstSoundCreateDTO createDTO) {
        InstSound instSound = new InstSound();

        Sound sound = new Sound();
        sound.setId(createDTO.getSoundId());
        instSound.setSound(sound);

        Instrumental instrumental = new Instrumental();
        instrumental.setId(createDTO.getInstrumentalId());
        instSound.setInstrumental(instrumental);

        instSound.setStepIndex(createDTO.getStepIndex());
        instSound.setRowIndex(createDTO.getRowIndex());

        return instSound;
    }
}
