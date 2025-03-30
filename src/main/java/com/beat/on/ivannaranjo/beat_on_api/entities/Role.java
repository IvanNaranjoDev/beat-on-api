package com.beat.on.ivannaranjo.beat_on_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity // Marca esta clase como una entidad JPA.
@Table(name = "roles") // Define el nombre de la tabla asociada a esta entidad.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users") // Excluye `users` para evitar problemas de recursión en toString.
@EqualsAndHashCode(exclude = "users") // Excluye `users` para evitar problemas de recursión en equals y hashCode.
public class Role {


    // Campo que almacena el identificador único del rol. Es autogenerado y clave primaria.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Nombre del rol, como "ROLE_ADMIN" o "ROLE_USER".
    @NotEmpty(message = "{msg.role.name.notEmpty}")
    @Size(max = 50, message = "{msg.role.name.size}")
    @Column(name = "role_name", nullable = false, unique = true, length = 50)
    private String name;


    // Relación muchos a muchos con la entidad `User`. Un rol puede pertenecer a muchos usuarios.
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users;


    /**
     * Constructor que excluye el campo `id`. Se utiliza para crear instancias de `Role`
     * cuando el `id` aún no se ha generado (por ejemplo, antes de insertarlo en la base de datos).
     * @param name Nombre del rol.
     */
    public Role(String name) {
        this.name = name;
    }
}