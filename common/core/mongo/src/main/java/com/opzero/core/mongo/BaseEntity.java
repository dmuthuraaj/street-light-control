package com.opzero.core.mongo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private boolean isActive;
    private boolean isDeleted;

    public BaseEntity() {
        this.isActive = true;
        this.isDeleted = false;
    }

    public BaseEntity(BaseEntity entity) {
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        this.isActive = entity.isActive();
        this.isDeleted = entity.isDeleted();
    }
}
