package com.example.rest_api;

import com.example.rest_api.service.PermissionService;
import com.example.rest_api.service.RoleService;
import com.example.rest_api.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
