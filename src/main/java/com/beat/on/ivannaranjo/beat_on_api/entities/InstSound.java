package com.beat.on.ivannaranjo.beat_on_api.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inst_sounds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstSound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sound_id", nullable = false)
    private Sound sound;

    @ManyToOne
    @JoinColumn(name = "instrumental_id", nullable = false)
    private Instrumental instrumental;

    @Column(name = "step_index", nullable = false)
    private int stepIndex;

    @Column(name = "row_index", nullable = false)
    private int rowIndex;

}
