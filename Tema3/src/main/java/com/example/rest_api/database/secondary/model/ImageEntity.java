package com.example.rest_api.database.secondary.model;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private AlbumEntity album;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private byte[] content;

    // Getters and Setters
}
