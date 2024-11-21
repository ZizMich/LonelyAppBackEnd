package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.UploadedPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * This interface represents a repository for managing {@link UploadedPhotoEntity} entities.
 * It extends Spring Data JPA's {@link JpaRepository} interface, providing basic CRUD operations
 * and additional methods for custom queries.
 */
public interface PhotoRepository extends JpaRepository<UploadedPhotoEntity, Long> {

}
