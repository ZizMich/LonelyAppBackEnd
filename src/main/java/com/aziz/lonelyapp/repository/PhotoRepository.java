package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.UploadedPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<UploadedPhotoEntity, Long> {

}
