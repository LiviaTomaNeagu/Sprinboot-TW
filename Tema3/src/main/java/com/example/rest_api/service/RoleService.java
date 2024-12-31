package com.example.rest_api.service;

import com.example.rest_api.database.primary.model.RoleEntity;
import com.example.rest_api.database.primary.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(RoleEntity role) {
        this.roleRepository.save(role);
    }

    public Boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    public RoleEntity getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found!"));
    }

    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

    public Optional<RoleEntity> getByName(String name) {
        return roleRepository.findByName(name);
    }

    public boolean hasPermissionForRole(String roleName, String permission, String albumEndpoint) {
        return roleRepository.hasPermissionForRole(roleName, permission, albumEndpoint);
    }

    public List<RoleEntity> getRolesByUserId(Long userId) {
        return roleRepository.findRolesByUserId(userId);
    }

}
