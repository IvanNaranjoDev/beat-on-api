package com.beat.on.ivannaranjo.beat_on_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StepDTO {
    private Long soundId;
    private int rowIndex;
    private List<Boolean> steps;;
}
