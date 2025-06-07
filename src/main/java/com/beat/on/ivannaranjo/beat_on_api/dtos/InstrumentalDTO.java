package com.beat.on.ivannaranjo.beat_on_api.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InstrumentalDTO {
    private Long id;
    private String instName;
    private String bpm;
    private boolean isPublic;
    private String coverUrl;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long userId;
    private List<StepDTO> steps;
}