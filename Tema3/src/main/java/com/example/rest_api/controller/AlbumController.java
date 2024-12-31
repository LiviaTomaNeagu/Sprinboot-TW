package com.example.rest_api.controller;

import com.example.rest_api.database.secondary.model.AlbumEntity;
import com.example.rest_api.database.secondary.model.ImageEntity;
import com.example.rest_api.security.AuthenticatedUser;
import com.example.rest_api.service.AlbumService;
import com.example.rest_api.service.ImageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/resources/album")
public class AlbumController {

    private final AlbumService albumService;
    private final ImageService imageService;

    public AlbumController(AlbumService albumService, ImageService imageService) {
        this.albumService = albumService;
        this.imageService = imageService;
    }

    // View album page with images
    @GetMapping("/{albumId}")
    public String viewAlbum(@PathVariable Long albumId, Model model) {
        AuthenticatedUser authenticatedUser = getAuthenticatedUser();

        AlbumEntity album = albumService.getAlbumById(albumId);
        List<ImageEntity> images = imageService.getImagesByAlbumId(albumId);

        boolean canPostOrPut = albumService.hasPermission(albumId, "POST", authenticatedUser.getId()) || albumService.hasPermission(albumId, "PUT", authenticatedUser.getId());
        boolean canDelete = albumService.hasPermission(albumId, "DELETE", authenticatedUser.getId());

        model.addAttribute("album", album);
        model.addAttribute("images", images);
        model.addAttribute("canPostOrPut", canPostOrPut);
        model.addAttribute("canDelete", canDelete);
        return "album"; // Thymeleaf template: album.html
    }

    // Display the Add Image page
    @GetMapping("/add-image/{albumId}")
    public String showAddImagePage(@PathVariable Long albumId, Model model) {
        AlbumEntity album = albumService.getAlbumById(albumId);
        model.addAttribute("album", album);
        return "add-image"; // Thymeleaf template: add-image.html
    }

    @GetMapping("/delete-image/{albumId}")
    public String showDeleteImagePage(@PathVariable Long albumId, Model model) {
        AlbumEntity album = albumService.getAlbumById(albumId);
        // Fetch all images for the given album ID
        List<ImageEntity> images = imageService.getImagesByAlbumId(albumId);

        // Add data to the model for rendering
        model.addAttribute("album", album);
        model.addAttribute("images", images);

        return "delete-image"; // Thymeleaf template: add-image.html
    }

    // Handle Add Image form submission
    @PostMapping("/{albumId}")
    public String addImage(@PathVariable Long albumId,
                           @RequestParam("image") MultipartFile image) {
        imageService.saveImage(albumId, image);
        return "redirect:/resources/album/" + albumId; // Redirect to the album view page
    }

    // Handle Delete Image form submission
    @PostMapping("/delete-image/{albumId}")
    public String deleteImages(
            @PathVariable Long albumId, // Get albumId from the path
            @RequestParam List<Long> imageIds // Get imageIds from the form
    ) {
        System.out.println("Album ID: " + albumId);
        System.out.println("Image IDs: " + imageIds);

        imageService.deleteImages(imageIds);
        return "redirect:/resources/album/" + albumId; // Redirect to the album view page
    }


    public AuthenticatedUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser) {
            return (AuthenticatedUser) authentication.getPrincipal();
        }
        throw new IllegalStateException("No authenticated user found");
    }
}
