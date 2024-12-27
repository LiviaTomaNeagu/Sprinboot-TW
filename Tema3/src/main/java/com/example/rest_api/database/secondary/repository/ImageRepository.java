package com.example.rest_api.database.secondary.repository;

import com.example.rest_api.database.secondary.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    List<ImageEntity> findByAlbumId(Long albumId);
}

