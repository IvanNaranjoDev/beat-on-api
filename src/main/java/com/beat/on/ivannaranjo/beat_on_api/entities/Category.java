package com.beat.on.ivannaranjo.beat_on_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    @Size(max = 50)
    @NotEmpty
    private String name;

    @Column(name = "color", nullable = false)
    @Size(max = 7)
    @NotEmpty
    private String color;

    @Column(name = "icon_url", nullable = false)
    @Size(max = 255)
    @NotEmpty
    private String iconUrl;
}
