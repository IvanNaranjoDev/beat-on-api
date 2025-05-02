package com.beat.on.ivannaranjo.beat_on_api.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "avatars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path_url", nullable = false)
    private String pathUrl;
}

