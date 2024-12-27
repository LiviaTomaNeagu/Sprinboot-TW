package com.example.rest_api.database.primary.model;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class PermissionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "method", nullable = false)
    private String method; // HTTP method, e.g., GET, POST

    @Column(name = "endpoint", nullable = false)
    private String endpoint; // Endpoint, e.g., /**

    // Default constructor
    public PermissionsEntity() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "PermissionsEntity{" +
                "id=" + id +
                ", method='" + method + '\'' +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }

}

