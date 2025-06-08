package com.beat.on.ivannaranjo.beat_on_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstSoundDTO {
    private Long id;
    private Long soundId;
    private Long instrumentalId;
    private int stepIndex;
    private int rowIndex;
}
