package com.beat.on.ivannaranjo.beat_on_api.services;

import com.beat.on.ivannaranjo.beat_on_api.dtos.InstSoundCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.InstSoundDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.StepDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.InstSound;
import com.beat.on.ivannaranjo.beat_on_api.entities.Instrumental;
import com.beat.on.ivannaranjo.beat_on_api.entities.Sound;
import com.beat.on.ivannaranjo.beat_on_api.mappers.InstSoundMapper;
import com.beat.on.ivannaranjo.beat_on_api.repositories.InstSoundRepository;
import com.beat.on.ivannaranjo.beat_on_api.repositories.InstrumentalRepository;
import com.beat.on.ivannaranjo.beat_on_api.repositories.SoundRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InstSoundService {

    @Autowired
    private InstSoundRepository instSoundRepository;

    @Autowired
    private InstrumentalRepository instrumentalRepository;

    @Autowired
    private SoundRepository soundRepository;

    @Autowired
    private InstSoundMapper instSoundMapper;

    public List<InstSoundDTO> getAllInstSounds(){
        List<InstSound> instSounds = instSoundRepository.findAll();
        return instSounds.stream()
                .map(instSoundMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<InstSoundDTO> getInstSoundById(Long id){
        Optional<InstSound> instSound = instSoundRepository.findById(id);
        return instSound.map(instSoundMapper::toDto);
    }

    public InstSoundDTO createInstSound(InstSoundCreateDTO createDTO){
        InstSound instSound = instSoundMapper.toEntity(createDTO);
        InstSound savedInstSound = instSoundRepository.save(instSound);
        return instSoundMapper.toDto(savedInstSound);
    }

    @Transactional
    public void updateInstSounds(Long instrumentalId, List<StepDTO> steps) {
        instSoundRepository.deleteByInstrumentalId(instrumentalId);

        Instrumental instrumental = instrumentalRepository.findById(instrumentalId)
                .orElseThrow(() -> new IllegalArgumentException("Instrumental no encontrada"));

        List<InstSound> newInstSounds = new ArrayList<>();

        for (StepDTO step : steps) {
            Sound sound = soundRepository.findById(step.getSoundId())
                    .orElseThrow(() -> new IllegalArgumentException("Sound no encontrado"));

            for (int i = 0; i < step.getSteps().size(); i++) {
                if (Boolean.TRUE.equals(step.getSteps().get(i))) {  // paso activo
                    InstSound instSound = InstSound.builder()
                            .instrumental(instrumental)
                            .sound(sound)
                            .rowIndex(step.getRowIndex())
                            .stepIndex(i)
                            .build();
                    newInstSounds.add(instSound);
                }
            }
        }
        instSoundRepository.saveAll(newInstSounds);
    }

    public void deleteInstSound(Long id) {
        InstSound existingInstSound = instSoundRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La posición no existe"));

        instSoundRepository.deleteById(id);
    }

    public List<InstSoundDTO> getInstSoundsByInstrumentalId(Long instrumentalId) {
        List<InstSound> sounds = instSoundRepository.findByInstrumentalIdOrderByRowIndexAscStepIndexAsc(instrumentalId);
        return sounds.stream().map(instSoundMapper::toDto).toList();
    }

    public List<StepDTO> getSequencerRowsByInstrumentalId(Long instrumentalId) {
        List<InstSound> instSounds = instSoundRepository.findByInstrumentalIdOrderByRowIndexAscStepIndexAsc(instrumentalId);

        Map<String, StepDTO> map = new HashMap<>();

        for (InstSound is : instSounds) {
            String key = is.getSound().getId() + "-" + is.getRowIndex();

            StepDTO stepDTO = map.computeIfAbsent(key,
                    k -> new StepDTO(is.getSound().getId(), is.getRowIndex())
            );

            stepDTO.activateStep(is.getStepIndex());
        }

        return new ArrayList<>(map.values());
    }
}
