package com.beat.on.ivannaranjo.beat_on_api.services;

import com.beat.on.ivannaranjo.beat_on_api.dtos.SoundCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.SoundDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Category;
import com.beat.on.ivannaranjo.beat_on_api.entities.Sound;
import com.beat.on.ivannaranjo.beat_on_api.mappers.SoundMapper;
import com.beat.on.ivannaranjo.beat_on_api.repositories.CategoryRepository;
import com.beat.on.ivannaranjo.beat_on_api.repositories.SoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SoundService {

    @Autowired
    private SoundRepository soundRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SoundMapper soundMapper;

    @Autowired
    private FileStorageService fileStorageService;

    public List<SoundDTO> getAllSounds() {
        return soundRepository.findAll().stream()
                .map(soundMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<SoundDTO> getSoundById(Long id) {
        return soundRepository.findById(id)
                .map(soundMapper::toDTO);
    }

    public SoundDTO createSound(SoundCreateDTO createDTO) {
        String imageFileName = null;
        String soundFileName = null;

        if (createDTO.getImage() != null && !createDTO.getImage().isEmpty()) {
            imageFileName = fileStorageService.saveFile(createDTO.getImage());
            if (imageFileName == null) {
                throw new RuntimeException("Error al guardar la imagen.");
            }
        }

        if (createDTO.getSoundPath() != null && !createDTO.getSoundPath().isEmpty()) {
            soundFileName = fileStorageService.saveFile(createDTO.getSoundPath());
            if (soundFileName == null) {
                throw new RuntimeException("Error al guardar el archivo de sonido.");
            }
        }

        // Buscar la categoría por ID
        Category category = categoryRepository.findById(createDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        // Mapear solo campos básicos, la categoría se asigna manualmente
        Sound sound = soundMapper.toEntity(createDTO);
        sound.setCategory(category);
        sound.setImage(imageFileName);
        sound.setSoundPath(soundFileName);

        Sound savedSound = soundRepository.save(sound);
        return soundMapper.toDTO(savedSound);
    }

    public SoundDTO updateSound(Long id, SoundCreateDTO updateDTO) {
        Sound existingSound = soundRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El sonido no existe."));

        String imageFileName = existingSound.getImage();
        if (updateDTO.getImage() != null && !updateDTO.getImage().isEmpty()) {
            imageFileName = fileStorageService.saveFile(updateDTO.getImage());
            if (imageFileName == null) {
                throw new RuntimeException("Error al guardar la nueva imagen.");
            }
        }

        String soundFileName = existingSound.getSoundPath();
        if (updateDTO.getSoundPath() != null && !updateDTO.getSoundPath().isEmpty()) {
            soundFileName = fileStorageService.saveFile(updateDTO.getSoundPath());
            if (soundFileName == null) {
                throw new RuntimeException("Error al guardar el archivo de sonido.");
            }
        }

        Category category = categoryRepository.findById(updateDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        existingSound.setName(updateDTO.getName());
        existingSound.setDuration(updateDTO.getDuration());
        existingSound.setEnabled(updateDTO.getEnabled());
        existingSound.setImage(imageFileName);
        existingSound.setSoundPath(soundFileName);
        existingSound.setCategory(category);

        Sound updatedSound = soundRepository.save(existingSound);
        return soundMapper.toDTO(updatedSound);
    }

    public void deleteSound(Long id) {
        Sound sound = soundRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El sonido no existe."));

        if (sound.getImage() != null && !sound.getImage().isEmpty()) {
            fileStorageService.deleteFile(sound.getImage());
        }

        if (sound.getSoundPath() != null && !sound.getSoundPath().isEmpty()) {
            fileStorageService.deleteFile(sound.getSoundPath());
        }

        soundRepository.deleteById(id);
    }
}