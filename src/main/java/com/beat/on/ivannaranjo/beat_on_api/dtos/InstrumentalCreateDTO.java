package com.beat.on.ivannaranjo.beat_on_api.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class InstrumentalCreateDTO {
        private String instName;
        private String bpm;
        private boolean isPublic;
        private MultipartFile coverUrl;
        private Long userId;
        private List<StepDTO> steps;
}
