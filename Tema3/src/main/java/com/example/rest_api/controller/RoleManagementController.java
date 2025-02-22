package com.example.rest_api.controller;

import com.example.rest_api.database.primary.model.PermissionsEntity;
import com.example.rest_api.database.primary.model.RoleEntity;
import com.example.rest_api.security.AuthenticatedUser;
import com.example.rest_api.service.PermissionService;
import com.example.rest_api.service.RoleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoleManagementController {

    private final RoleService roleService;
    private final PermissionService permissionService;

    public RoleManagementController(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @GetMapping("/role-management")
    public String roleManagementPage(Model model) {

        AuthenticatedUser authenticatedUser = getAuthenticatedUser();
        boolean canEditOrDelete = roleService.canEditOrDelete(authenticatedUser.getId());

        // Fetch all roles
        List<RoleEntity> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("canEditOrDelete", canEditOrDelete);
        return "role-management"; // Renders role-management.html
    }

    @GetMapping("/role-management/edit/{id}")
    public String editRolePage(@PathVariable Long id, Model model) {
        RoleEntity role = roleService.getRoleById(id);
        List<PermissionsEntity> allPermissions = permissionService.getAllPermissions();

        model.addAttribute("role", role);
        model.addAttribute("allPermissions", allPermissions);
        return "edit-role";
    }

    @PostMapping("/role-management/edit")
    public String saveRoleChanges(@RequestParam Long id,
                                  @RequestParam String name,
                                  @RequestParam(required = false) List<Long> permissions) {
        RoleEntity role = roleService.getRoleById(id);

        role.setName(name);

        List<PermissionsEntity> updatedPermissions = permissions != null
                ? permissions.stream().map(permissionService::getById).toList()
                : new ArrayList<>(); // Use mutable collection
        role.getPermissions().clear(); // Clear existing permissions
        role.getPermissions().addAll(updatedPermissions); // Add new permissions

        roleService.save(role);
        return "redirect:/role-management";
    }

    @GetMapping("/role-management/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return "redirect:/role-management";
    }

    public AuthenticatedUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser) {
            return (AuthenticatedUser) authentication.getPrincipal();
        }
        throw new IllegalStateException("No authenticated user found");
    }
}
