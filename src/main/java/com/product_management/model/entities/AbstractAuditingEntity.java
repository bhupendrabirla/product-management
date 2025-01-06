package com.product_management.model.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractAuditingEntity implements Serializable {

    private ZonedDateTime createdDate;

    private ZonedDateTime lastModifiedDate;

    private String createdBy;

    private String lastModifiedBy;


    /**
     * This method is called before the entity is persisted into the database.
     * It automatically sets the creation timestamp and delegates to {@link #prePersistAndPreUpdate()}
     * to set the modification timestamp.
     */
    @PrePersist
    public void prePersist() {
        createdDate = ZonedDateTime.now();
        prePersistAndPreUpdate();
    }

    /**
     * This method is called before the entity is updated in the database.
     * It delegates to {@link #prePersistAndPreUpdate()} to update the modification timestamp.
     */
    @PreUpdate
    public void preUpdate() {
        prePersistAndPreUpdate();
    }

    public void prePersistAndPreUpdate() {
        lastModifiedDate = ZonedDateTime.now();
    }

}
