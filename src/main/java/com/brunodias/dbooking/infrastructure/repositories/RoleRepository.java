package com.brunodias.dbooking.infrastructure.repositories;

import com.brunodias.dbooking.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String role);

    boolean existsByName(String role);
}
