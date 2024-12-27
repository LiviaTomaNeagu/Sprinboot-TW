package com.example.rest_api.database.primary.repository;

import com.example.rest_api.database.primary.model.PermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionsEntity, Long> {
    Optional<PermissionsEntity> findByMethodAndEndpoint(String method, String endpoint);
}
