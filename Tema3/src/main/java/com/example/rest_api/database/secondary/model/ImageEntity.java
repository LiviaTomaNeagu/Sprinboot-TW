package com.example.rest_api.database.secondary.model;

import com.example.rest_api.database.secondary.model.AlbumEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private AlbumEntity album; // Foreign key relationship to albums table

    @Column(nullable = false)
    private String name; // Name of the image

    @Lob
    @Column(nullable = false)
    private byte[] content; // Binary content of the image

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // Timestamp for when the image was added

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
