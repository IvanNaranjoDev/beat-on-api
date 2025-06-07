package com.beat.on.ivannaranjo.beat_on_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "instrumentals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instrumental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inst_name", length = 50, nullable = false, unique = true)
    @Size(max = 50)
    private String instName;

    @Column(length = 3, nullable = false)
    @Size(max = 50)
    private String bpm;

    @Column(name = "public", nullable = false)
    private boolean isPublic;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @Column(name = "cover_url", nullable = false, length = 255)
    private String coverUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
