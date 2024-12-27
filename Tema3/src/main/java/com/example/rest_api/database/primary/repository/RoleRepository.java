package com.example.rest_api.database.primary.repository;

import com.example.rest_api.database.primary.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    Boolean existsByName(String name);
}
