package com.beat.on.ivannaranjo.beat_on_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StepDTO {
    private Long soundId;
    private int rowIndex;
    private List<Boolean> steps;

    public StepDTO(Long soundId, int rowIndex) {
        this.soundId = soundId;
        this.rowIndex = rowIndex;
        this.steps = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            this.steps.add(Boolean.FALSE);
        }
    }

    public void activateStep(int index) {
        if (index >= 0 && index < steps.size()) {
            steps.set(index, Boolean.TRUE);
        }
    }
}
