package com.beat.on.ivannaranjo.beat_on_api.repositories;

import com.beat.on.ivannaranjo.beat_on_api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
