package com.beat.on.ivannaranjo.beat_on_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "sounds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Sound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sound_name", nullable = false)
    @Size(max = 50)
    @NotEmpty
    private String name;

    @Column(name = "duration", nullable = false)
    @Size(max = 10)
    @NotEmpty
    private String duration;

    @Column(name = "image_url", nullable = false)
    @Size(max = 255)
    @NotEmpty
    private String image;

    @Column(name = "sound_path", nullable = false)
    @Size(max = 255)
    @NotEmpty
    private String soundPath;

    @Column(name = "enabled", nullable = false)
    @NotNull
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
