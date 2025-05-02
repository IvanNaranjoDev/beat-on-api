package com.beat.on.ivannaranjo.beat_on_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Set;

@Entity // Marca esta clase como una entidad JPA.
@Table(name = "users") // Define el nombre de la tabla asociada a esta entidad.
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roles") // Excluye roles para evitar problemas de recursión en el toString.
@EqualsAndHashCode(exclude = "roles") // Excluye roles para evitar recursión en equals y hashCode.
@EntityListeners(AuditingEntityListener.class) // Habilita las anotaciones de auditoría.
public class User {


    // Campo que almacena el identificador único del usuario. Es autogenerado y clave primaria.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Campo que almacena el nombre de usuario. Actúa como la clave primaria.
    @NotEmpty(message = "{msg.user.username.notEmpty}")
    @Size(max = 50, message = "{msg.user.username.size}")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;


    // Campo que almacena el correo electronico
    @Size(max = 100, message = "{msg.user.username.size}")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // Campo que almacena la contraseña encriptada del usuario.
    @NotEmpty(message = "{msg.user.password.notEmpty}")
    @Size(min = 8, message = "{msg.user.password.size}")
    @Column(name = "passcode", nullable = false)
    private String password;


    // Campo que indica si el usuario está habilitado.
    @NotNull(message = "{msg.user.enabled.notNull}")
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;


    // Relación muchos a muchos con la entidad `Role`.
    // Se establece FetchType.EAGER para que se carguen los roles junto al usuario
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}