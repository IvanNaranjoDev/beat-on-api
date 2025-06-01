package com.beat.on.ivannaranjo.beat_on_api.mappers;

import com.beat.on.ivannaranjo.beat_on_api.dtos.SoundCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.SoundDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Category;
import com.beat.on.ivannaranjo.beat_on_api.entities.Sound;
import org.springframework.stereotype.Component;

@Component
public class SoundMapper {

    public SoundDTO toDTO(Sound sound) {
        SoundDTO dto = new SoundDTO();
        CategoryMapper categoryMapper = new CategoryMapper();

        dto.setId(sound.getId());
        dto.setName(sound.getName());
        dto.setDuration(sound.getDuration());
        dto.setImage(sound.getImage());
        dto.setSoundPath(sound.getSoundPath());
        dto.setEnabled(sound.isEnabled());
        dto.setCategory(categoryMapper.toDTO(sound.getCategory()));

        return dto;
    }

    public Sound toEntity(SoundDTO dto) {
        Sound sound = new Sound();
        CategoryMapper categoryMapper = new CategoryMapper();

        sound.setId(dto.getId());
        sound.setName(dto.getName());
        sound.setDuration(dto.getDuration());
        sound.setImage(dto.getImage());
        sound.setSoundPath(dto.getSoundPath());
        sound.setEnabled(dto.getEnabled());
        sound.setCategory(categoryMapper.toEntity(dto.getCategory()));

        return sound;
    }

    public Sound toEntity(SoundCreateDTO createDTO) {
        Sound sound = new Sound();

        sound.setName(createDTO.getName());
        sound.setDuration(createDTO.getDuration());
        sound.setEnabled(createDTO.getEnabled());

        if (createDTO.getCategoryId() != null) {
            Category category = new Category();
            category.setId(createDTO.getCategoryId());
            sound.setCategory(category);
        } else {
            sound.setCategory(null);
        }

        return sound;
    }
}
