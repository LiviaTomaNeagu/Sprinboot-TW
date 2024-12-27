package com.example.rest_api.service;

import com.example.rest_api.database.secondary.model.AlbumEntity;
import com.example.rest_api.database.secondary.model.ImageEntity;
import com.example.rest_api.database.secondary.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final AlbumService albumService;
    private final FileStorageService fileStorageService;

    public ImageService(ImageRepository imageRepository, AlbumService albumService, FileStorageService fileStorageService) {
        this.imageRepository = imageRepository;
        this.albumService = albumService;
        this.fileStorageService = fileStorageService;
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
    public void saveImage(Long albumId, MultipartFile imageFile) {
        try {
            // Save the file to local storage
            String filePath = fileStorageService.saveFile(imageFile);

            // Save metadata to the database
            ImageEntity image = new ImageEntity();
            image.setAlbum(albumService.getAlbumById(albumId));
            image.setName(imageFile.getOriginalFilename());
            image.setFilePath(imageFile.getOriginalFilename()); // Save file path in DB
            image.setCreatedAt(LocalDateTime.now());
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
    }

    /**
     * Delete selected images by their IDs.
     */
    public void deleteImages(List<Long> imageIds) {
        imageRepository.deleteImagesByIds(imageIds);
    }
}
