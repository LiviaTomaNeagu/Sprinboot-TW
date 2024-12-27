package com.example.rest_api.controller;

import com.example.rest_api.database.secondary.model.AlbumEntity;
import com.example.rest_api.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String addAlbum(@ModelAttribute AlbumEntity album) {
        albumService.saveAlbum(album);
        return "redirect:/resources";
    }
}

