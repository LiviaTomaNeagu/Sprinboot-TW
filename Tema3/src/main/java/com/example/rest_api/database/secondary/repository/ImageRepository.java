package com.example.rest_api.database.secondary.repository;

import com.example.rest_api.database.secondary.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    List<ImageEntity> findByAlbumId(Long albumId);

    @Modifying
    @Query("DELETE FROM ImageEntity i WHERE i.id IN :imageIds")
    void deleteImagesByIds(@Param("imageIds") List<Long> imageIds);

    @Modifying
    @Query("DELETE FROM ImageEntity i WHERE i.id = :id")
    void deleteById(@Param("id") Long id);
}

