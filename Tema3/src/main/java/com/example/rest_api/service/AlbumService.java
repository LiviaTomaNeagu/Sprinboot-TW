package com.example.rest_api.service;

import com.example.rest_api.database.secondary.model.AlbumEntity;
import com.example.rest_api.database.secondary.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<AlbumEntity> getAllAlbums() {
        return albumRepository.findAll();
    }

    public AlbumEntity saveAlbum(AlbumEntity album) {
        return albumRepository.save(album);
    }
}

