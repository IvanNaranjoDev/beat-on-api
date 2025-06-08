package com.beat.on.ivannaranjo.beat_on_api.services;

import com.beat.on.ivannaranjo.beat_on_api.dtos.InstrumentalCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.InstrumentalDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.StepDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.InstSound;
import com.beat.on.ivannaranjo.beat_on_api.entities.Instrumental;
import com.beat.on.ivannaranjo.beat_on_api.entities.Sound;
import com.beat.on.ivannaranjo.beat_on_api.entities.User;
import com.beat.on.ivannaranjo.beat_on_api.mappers.InstrumentalMapper;
import com.beat.on.ivannaranjo.beat_on_api.repositories.InstSoundRepository;
import com.beat.on.ivannaranjo.beat_on_api.repositories.InstrumentalRepository;
import com.beat.on.ivannaranjo.beat_on_api.repositories.SoundRepository;
import com.beat.on.ivannaranjo.beat_on_api.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstrumentalService {
    private static final Logger logger = LoggerFactory.getLogger(InstrumentalService.class);

    @Autowired
    private InstrumentalRepository instrumentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstrumentalMapper instrumentalMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private InstSoundRepository instSoundRepository;

    @Autowired
    private SoundRepository soundRepository;

    public List<InstrumentalDTO> getAllInstrumentals() {
        try {
            List<Instrumental> instrumentals = instrumentalRepository.findAll();
            return instrumentals.stream().map(instrumentalMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener todos los instrumentales: {}", e.getMessage());
            throw new RuntimeException("Error al obtener los instrumentales.", e);
        }
    }

    public Optional<InstrumentalDTO> getInstrumentalById(Long id) {
        try {
            logger.info("Buscando instrumental con ID {}", id);
            return instrumentalRepository.findById(id).map(instrumentalMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar instrumental con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el instrumental.", e);
        }
    }

    public ResponseEntity<?> createInstrumental(@ModelAttribute InstrumentalCreateDTO instrumentalCreateDTO) {
        try {
            logger.info("Creando nuevo instrumental con nombre {}", instrumentalCreateDTO.getInstName());

            Optional<User> optionalUser = userRepository.findById(instrumentalCreateDTO.getUserId());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado.");
            }

            String fileName = null;
            MultipartFile coverFile = instrumentalCreateDTO.getCoverUrl();
            if (coverFile != null && !coverFile.isEmpty()) {
                fileName = fileStorageService.saveFile(coverFile);
                if (fileName == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen de portada.");
                }
            }

            Instrumental instrumental = instrumentalMapper.toEntity(instrumentalCreateDTO, optionalUser.get());
            instrumental.setCoverUrl(fileName);
            Instrumental savedInstrumental = instrumentalRepository.save(instrumental);


            List<StepDTO> steps = instrumentalCreateDTO.getSteps();
            if (steps != null && !steps.isEmpty()) {
                for (StepDTO stepDTO : steps) {
                    if (stepDTO.getSoundId() == null) continue;

                    Sound sound = soundRepository.findById(stepDTO.getSoundId())
                            .orElseThrow(() -> new RuntimeException("Sound not found: " + stepDTO.getSoundId()));

                    for (int i = 0; i < stepDTO.getSteps().size(); i++) {
                        if (Boolean.TRUE.equals(stepDTO.getSteps().get(i))) {
                            InstSound instSound = InstSound.builder()
                                    .instrumental(savedInstrumental)
                                    .sound(sound)
                                    .rowIndex(stepDTO.getRowIndex())
                                    .stepIndex(i)
                                    .build();
                            instSoundRepository.save(instSound);
                        }
                    }
                }
            }

            logger.info("Instrumental creado exitosamente con ID {}", savedInstrumental.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(instrumentalMapper.toDTO(savedInstrumental));

        } catch (Exception e) {
            logger.error("Error al crear el instrumental: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el instrumental.");
        }
    }

    public ResponseEntity<?> updateInstrumental(Long id, InstrumentalCreateDTO instrumentalCreateDTO) {
        try {
            logger.info("Actualizando instrumental con ID {}", id);

            if (instrumentalCreateDTO == null) {
                logger.error("El DTO recibido es null");
                return ResponseEntity.badRequest().body("Datos de actualización vacíos");
            }

            logger.info("Datos recibidos para actualización: nombre={}, bpm={}, public={}, userId={}",
                    instrumentalCreateDTO.getInstName(),
                    instrumentalCreateDTO.getBpm(),
                    instrumentalCreateDTO.isPublic(),
                    instrumentalCreateDTO.getUserId());

            Optional<Instrumental> optionalInstrumental = instrumentalRepository.findById(id);
            if (optionalInstrumental.isEmpty()) {
                logger.warn("Instrumental con ID {} no encontrado", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El instrumental no existe.");
            }

            Optional<User> optionalUser = userRepository.findById(instrumentalCreateDTO.getUserId());
            if (optionalUser.isEmpty()) {
                logger.warn("Usuario con ID {} no encontrado", instrumentalCreateDTO.getUserId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado.");
            }

            Instrumental instrumentalToUpdate = optionalInstrumental.get();
            instrumentalToUpdate.setInstName(instrumentalCreateDTO.getInstName());
            instrumentalToUpdate.setBpm(instrumentalCreateDTO.getBpm());
            instrumentalToUpdate.setPublic(instrumentalCreateDTO.isPublic());
            instrumentalToUpdate.setUser(optionalUser.get());

            MultipartFile coverFile = instrumentalCreateDTO.getCoverUrl();
            String fileName = instrumentalToUpdate.getCoverUrl();

            if (coverFile != null && !coverFile.isEmpty()) {
                logger.info("Se recibió archivo de portada con nombre original: {}", coverFile.getOriginalFilename());
                fileName = fileStorageService.saveFile(coverFile);
                if (fileName == null) {
                    logger.error("Error al guardar la imagen de portada.");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen de portada.");
                }
            } else {
                logger.info("No se recibió archivo de portada, se mantiene la existente");
            }
            instrumentalToUpdate.setCoverUrl(fileName);

            Instrumental updatedInstrumental = instrumentalRepository.save(instrumentalToUpdate);
            logger.info("Instrumental con ID {} actualizado correctamente", updatedInstrumental.getId());
            return ResponseEntity.ok(instrumentalMapper.toDTO(updatedInstrumental));

        } catch (Exception e) {
            logger.error("Error al actualizar el instrumental con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el instrumental.");
        }
    }

    public ResponseEntity<?> deleteInstrumental(Long id) {
        try {
            logger.info("Eliminando instrumental con ID {}", id);

            if (!instrumentalRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El instrumental no existe.");
            }

            instrumentalRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar el instrumental con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el instrumental.");
        }
    }
}
