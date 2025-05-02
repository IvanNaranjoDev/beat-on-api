package com.beat.on.ivannaranjo.beat_on_api.services;

import com.beat.on.ivannaranjo.beat_on_api.dtos.UserCreateDTO;
import com.beat.on.ivannaranjo.beat_on_api.dtos.UserDTO;
import com.beat.on.ivannaranjo.beat_on_api.entities.User;
import com.beat.on.ivannaranjo.beat_on_api.mappers.RoleMapper;
import com.beat.on.ivannaranjo.beat_on_api.mappers.UserMapper;
import com.beat.on.ivannaranjo.beat_on_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping
    public List<UserDTO> getAllUsers(){
        try{
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(userMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los usuarios: {}", e.getMessage());
            throw new RuntimeException("Error al obtener todos los usuarios.", e);
        }
    }

    public Optional<UserDTO> getUserById(Long id) {
        try {
            logger.info("Buscando usuario con ID {}", id);
            return userRepository.findById(id).map(userMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar usuario con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el usuario.", e);
        }
    }

    public ResponseEntity<?> createUser(@Valid UserCreateDTO userCreateDTO, Locale locale) {
        try {
            logger.info("Creando nuevo usuario con username {}", userCreateDTO.getUsername());

            if (userRepository.existsByEmail(userCreateDTO.getEmail()) && userRepository.existsByUsername(userCreateDTO.getUsername())) {
                logger.warn("Error al crear usuario: Email o usuario ya utilizados");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear usuario: Email o usuario ya utilizados");
            }

            User user = userMapper.toEntity(userCreateDTO);
            User savedUser = userRepository.save(user);

            logger.info("Usuario creado exitosamente con ID {}", savedUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(savedUser));
        } catch (Exception e) {
            logger.error("Error al crear el usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la usuario.");
        }
    }

    public ResponseEntity<?> updateUser(Long id, @Valid UserCreateDTO userCreateDTO, Locale locale) {
        try {
            logger.info("Actualizando el usuario con ID {}", id);

            Optional<User> existingUser = userRepository.findById(id);
            if (!existingUser.isPresent()) {
                logger.warn("No se encontró el usuario con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe.");
            }

            if (userRepository.existsByEmailAndIdNot(userCreateDTO.getEmail(), id)) {
                logger.warn("Error al actualizar el usuario: El email {} ya está en uso.", userCreateDTO.getEmail());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar, el email esta en uso");
            }

            User userToUpdate = existingUser.get();
            userToUpdate.setEmail(userCreateDTO.getEmail());
            userToUpdate.setUsername(userCreateDTO.getUsername());
            if (userCreateDTO.getPassword() != null && !userCreateDTO.getPassword().isEmpty()) {
                userToUpdate.setPassword(userCreateDTO.getPassword());
            }
            userToUpdate.setEnabled(userCreateDTO.getEnabled());
            userToUpdate.setAvatar(userMapper.toEntity(userCreateDTO).getAvatar());
            if (userCreateDTO.getRoles() != null) {
                userToUpdate.setRoles(
                        userCreateDTO.getRoles()
                                .stream()
                                .map(roleMapper::toEntity)
                                .collect(Collectors.toSet())
                );
            }

            User updatedUser = userRepository.save(userToUpdate);
            System.out.println(updatedUser);
            logger.info("Usuario con ID {} actualizado exitosamente.", id);
            return ResponseEntity.ok(userMapper.toDTO(updatedUser));
        } catch (Exception e) {
            logger.error("Error al actualizar usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario.");
        }
    }

    public ResponseEntity<?> deleteUser(Long id) {
        try {
            logger.info("Eliminando usuario con ID {}", id);

            if (!userRepository.existsById(id)) {
                logger.warn("No se encontró el con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe.");
            }

            userRepository.deleteById(id);

            logger.info("Usuario con ID {} eliminado exitosamente.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar el usuario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario.");
        }
    }
}
