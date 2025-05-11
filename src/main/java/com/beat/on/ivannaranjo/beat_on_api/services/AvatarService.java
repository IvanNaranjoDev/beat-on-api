package com.beat.on.ivannaranjo.beat_on_api.services;

import com.beat.on.ivannaranjo.beat_on_api.dtos.AvatarCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.AvatarDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.Avatar;
import com.beat.on.ivannaranjo.beat_on_api.mappers.AvatarMapper;
import com.beat.on.ivannaranjo.beat_on_api.repositories.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private AvatarMapper avatarMapper;

    @Autowired
    private FileStorageService fileStorageService;

    public List<AvatarDTO> getAllAvatars(){
        List<Avatar> avatars = avatarRepository.findAll();
        return avatars.stream()
                .map(avatarMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AvatarDTO> getAvatarById(Long id){
        Optional<Avatar> avatar = avatarRepository.findById(id);
        return avatar.map(avatarMapper::toDTO);
    }

    public AvatarDTO createAvatar(AvatarCreateDTO createDTO) {
        String fileName = null;
        if (createDTO.getPath() != null && !createDTO.getPath().isEmpty()) {
            fileName = fileStorageService.saveFile(createDTO.getPath());
            if (fileName == null) {
                throw new RuntimeException("Error al guardar la imagen.");
            }
        }

        Avatar avatar = avatarMapper.toEntity(createDTO);
        avatar.setPathUrl(fileName);

        Avatar savedAvatar = avatarRepository.save(avatar);
        return avatarMapper.toDTO(savedAvatar);
    }

    public AvatarDTO updateAvatar(Long id, AvatarCreateDTO updateDTO) {

        // Buscar el avatar existente
        Avatar existingAvatar = avatarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El avatar no existe."));

        // Procesar la imagen si se proporcionó
        String fileName = existingAvatar.getPathUrl(); // Conservar la imagen existente por defecto
        if (updateDTO.getPath() != null && !updateDTO.getPath().isEmpty()) {
            fileName = fileStorageService.saveFile(updateDTO.getPath());
            if (fileName == null) {
                throw new RuntimeException("Error al guardar la nueva imagen.");
            }
        }

        // Actualizar los datos del avatar
        existingAvatar.setPathUrl(fileName);

        // Guardar los cambios
        Avatar updateAvatar = avatarRepository.save(existingAvatar);

        // Convertir la entidad actualizada a DTO y devolverla
        return avatarMapper.toDTO(updateAvatar);
    }

    public void deleteAvatar(Long id) {

        // Buscar el avatar
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El avatar no existe."));

        // Eliminar la imagen asociada si existe
        if (avatar.getPathUrl() != null && !avatar.getPathUrl().isEmpty()) {
            fileStorageService.deleteFile(avatar.getPathUrl());
        }

        // Eliminar la categoría
        avatarRepository.deleteById(id);
    }
}
