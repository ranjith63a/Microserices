package com.microservices.department.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {


    @Column(name = "created_timestamp", updatable = false)
    private LocalDateTime createdTimestamp;

    @Column(name = "updated_timestamp")
    private LocalDateTime updatedTimestamp;

    @PrePersist
    protected void onCreate() {
        createdTimestamp = LocalDateTime.now();
        updatedTimestamp = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTimestamp = LocalDateTime.now();
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }

}
