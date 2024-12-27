package com.example.rest_api;

import com.example.rest_api.database.model.PermissionsEntity;
import com.example.rest_api.database.model.RoleEntity;
import com.example.rest_api.database.model.UserEntity;
import com.example.rest_api.service.PermissionService;
import com.example.rest_api.service.RoleService;
import com.example.rest_api.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final PermissionService permissionService;

    public DataInitializer(UserService userService, RoleService roleService, PermissionService permissionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

  @Override
  public void run(ApplicationArguments args) throws Exception {
  }
}
