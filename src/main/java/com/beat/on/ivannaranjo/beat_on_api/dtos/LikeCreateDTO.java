package com.beat.on.ivannaranjo.beat_on_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeCreateDTO {
    private Long userId;
    private Long instrumentalId;
}
