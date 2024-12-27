package com.example.rest_api.controller;

import com.example.rest_api.database.secondary.model.AlbumEntity;
import com.example.rest_api.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/resources")
public class ResourcesController {

    private final AlbumService albumService;

    public ResourcesController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public String resourcesPage(Model model) {
        List<AlbumEntity> albums = albumService.getAllAlbums();
        model.addAttribute("albums", albums);
        return "resources";
    }

    @GetMapping("/add-album")
    public String addAlbumPage(Model model) {
        model.addAttribute("album", new AlbumEntity());
        return "add-album";
    }

    @PostMapping("/add-album")
    public String addAlbum(@RequestParam("name") String name,
                           @RequestParam("description") String description) {
        AlbumEntity album = new AlbumEntity();
        album.setName(name);
        album.setDescription(description);
        albumService.saveAlbum(album);
        return "redirect:/resources";
    }
}

