package com.example.rest_api.service;
import com.example.rest_api.database.model.PermissionsEntity;
import com.example.rest_api.database.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public boolean existsByMethodAndEndpoint(String method, String endpoint) {
        return permissionRepository.findByMethodAndEndpoint(method, endpoint).isPresent();
    }

    public PermissionsEntity getByMethodAndEndpoint(String method, String endpoint) {
        return permissionRepository.findByMethodAndEndpoint(method, endpoint)
                .orElseThrow(() -> new RuntimeException("Permission not found!"));
    }

    public PermissionsEntity save(PermissionsEntity permission) {
        return permissionRepository.save(permission);
    }

    public List<PermissionsEntity> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public PermissionsEntity getById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found!"));
    }
}
