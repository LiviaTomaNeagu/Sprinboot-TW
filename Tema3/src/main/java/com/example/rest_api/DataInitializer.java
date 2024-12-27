package com.example.rest_api;

import com.example.rest_api.database.model.Role;
import com.example.rest_api.database.model.RoleEntity;
import com.example.rest_api.database.model.UserEntity;
import com.example.rest_api.service.RoleService;
import com.example.rest_api.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {
    private final UserService userService;
    private final RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        RoleEntity admin = new RoleEntity();
        admin.setRole(Role.ADMIN);

        RoleEntity user = new RoleEntity();
        user.setRole(Role.USER);

        Boolean isRoleAdminInDb = roleService.existsByName(admin.getName());
        if(!isRoleAdminInDb)
            roleService.save(admin);

        Boolean isRoleUserInDb = roleService.existsByName(user.getName());
        if(!isRoleUserInDb)
            roleService.save(user);

        UserEntity adminUser = new UserEntity();
        adminUser.setUsername("ADMIN");
        adminUser.setEmail("admin@admin.com");
        adminUser.setPassword("admin");

        adminUser.addRole(admin);
        Boolean isUserPresentInDatabase = this.userService.existsByEmail(adminUser.getEmail());
        if(!isUserPresentInDatabase)
            this.userService.save(adminUser);
    }
}
