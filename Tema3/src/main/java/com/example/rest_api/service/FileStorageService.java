package com.example.rest_api.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private static final String STORAGE_DIR = "C:/Facultate/ANUL III SEM I/TW/Sprinboot-TW/Tema3/image-storage";

    public String saveFile(MultipartFile file) throws IOException {
        // Ensure the storage directory exists
        Path storagePath = Paths.get(STORAGE_DIR);
        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        // Save the file
        String fileName = file.getOriginalFilename();
        Path filePath = storagePath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        // Return the relative path for use in the database
        return fileName; // Only the file name is returned
    }
}
