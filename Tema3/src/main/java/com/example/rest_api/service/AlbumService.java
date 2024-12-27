package com.example.rest_api.service;

import com.example.rest_api.database.primary.model.Methods;
import com.example.rest_api.database.primary.model.PermissionsEntity;
import com.example.rest_api.database.secondary.model.AlbumEntity;
import com.example.rest_api.database.secondary.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.rest_api.database.primary.model.RoleEntity;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final RoleService roleService;

    public AlbumService(AlbumRepository albumRepository, RoleService roleService) {
        this.albumRepository = albumRepository;
        this.roleService = roleService;
    }

    public List<AlbumEntity> getAllAlbums() {
        return albumRepository.findAll();
    }

    public AlbumEntity saveAlbum(AlbumEntity album) {

        if (album.getName() == null || album.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Album name cannot be null or empty");
        }

        // Save the album
        AlbumEntity savedAlbum = albumRepository.save(album);

        // Automatically create roles for the new album
        String albumName = savedAlbum.getName().toUpperCase(); // Normalize name for roles

        // Create ALBUM_NAME_ADMIN role
        RoleEntity adminRole = new RoleEntity();
        adminRole.setName(albumName + "_ADMIN");
        adminRole.setPermissions(List.of(
                createPermission(Methods.GET.toString(), "/album/" + albumName + "/**"),
                createPermission(Methods.PUT.toString(), "/album/" + albumName + "/**"),
                createPermission(Methods.POST.toString(), "/album/" + albumName + "/**"),
                createPermission(Methods.DELETE.toString(), "/album/" + albumName + "/**")
        ));
        roleService.save(adminRole);

        // Create ALBUM_NAME role
        RoleEntity userRole = new RoleEntity();
        userRole.setName(albumName);
        userRole.setPermissions(List.of(
                createPermission(Methods.GET.toString(), "/album/" + albumName + "/**")
        ));
        roleService.save(userRole);

        return savedAlbum;
    }

    private PermissionsEntity createPermission(String method, String endpoint) {
        PermissionsEntity permission = new PermissionsEntity();
        permission.setMethod(method);
        permission.setEndpoint(endpoint);
        return permission;
    }
}

