package com.example.rest_api.database.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "role")
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* See Role enum class from config.util for the default roles */
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<UserEntity> users = new ArrayList<>();

    public void addUser(UserEntity user) {
        users.add(user);
    }

    public void setRole(Role role) {
        this.name= role.name();
    }

    @Override
    public String getAuthority() {
        return name;
    }
}