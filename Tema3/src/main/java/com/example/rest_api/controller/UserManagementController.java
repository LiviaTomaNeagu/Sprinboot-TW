package com.example.rest_api.controller;

import com.example.rest_api.database.primary.model.UserEntity;
import com.example.rest_api.database.primary.model.RoleEntity;
import com.example.rest_api.service.UserService;
import com.example.rest_api.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserManagementController {

    private final UserService userService;
    private final RoleService roleService;

    public UserManagementController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user-management")
    public String userManagementPage(Model model) {
        // Fetch all users
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-management"; // Renders user-management.html
    }

    @GetMapping("/user-management/update-roles/{id}")
    public String updateRolesPage(@PathVariable Long id, Model model) {
        // Fetch user and all available roles
        UserEntity user = userService.getUserById(id);
        List<RoleEntity> allRoles = roleService.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", allRoles);
        return "update-roles"; // Renders update-roles.html
    }

    @PostMapping("/user-management/update-roles")
    public String updateRoles(@RequestParam Long userId, @RequestParam List<Long> roles) {
        // Fetch the user from the database
        UserEntity user = userService.getUserById(userId);

        // Clear the existing roles
        user.getRoles().clear();

        // Fetch and add the new roles to the user's roles collection
        roles.forEach(roleId -> {
            RoleEntity role = roleService.getRoleById(roleId);
            user.getRoles().add(role); // Add each role individually
        });

        // Save the user with updated roles
        userService.save(user);

        return "redirect:/user-management";
    }

}
