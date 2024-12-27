package com.example.rest_api.service;

import com.example.rest_api.database.secondary.model.AlbumEntity;
import com.example.rest_api.database.secondary.model.ImageEntity;
import com.example.rest_api.database.secondary.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final AlbumService albumService;

    public ImageService(ImageRepository imageRepository, AlbumService albumService) {
        this.imageRepository = imageRepository;
        this.albumService = albumService;
    }

    /**
     * Get all images for a specific album.
     */
    public List<ImageEntity> getImagesByAlbumId(Long albumId) {
        return imageRepository.findByAlbumId(albumId);
    }

    /**
     * Save an image to the album.
     */
    public ImageEntity saveImage(Long albumId, MultipartFile imageFile) {
        AlbumEntity album = albumService.getAlbumById(albumId);

        // Save the image
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setAlbum(album);
        imageEntity.setName(imageFile.getOriginalFilename());

        try {
            imageEntity.setContent(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }

        return imageRepository.save(imageEntity);
    }

    /**
     * Delete selected images by their IDs.
     */
    public void deleteImages(List<Long> imageIds) {
        imageRepository.deleteAllById(imageIds);
    }
}
