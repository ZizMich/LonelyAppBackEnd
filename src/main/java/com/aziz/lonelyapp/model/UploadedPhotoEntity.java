package com.aziz.lonelyapp.model;

import jakarta.persistence.*;

/**
 * This class represents an entity for uploaded photos in the application.
 * It is mapped to the "uploaded_photos" table in the database.
 */
@Entity
@Table(name ="uploaded_photos" )
public class UploadedPhotoEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="target")
    private String target;

    @Column(name= "createdat")
    private Long createdAt;

    @Column(name = "fromuser")
    private String fromUser;

    /**
     * Sets the unique identifier for the uploaded photo.
     *
     * @param id The unique identifier.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the unique identifier for the uploaded photo.
     *
     * @return The unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the target of the uploaded photo.
     *
     * @return The target.
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the target of the uploaded photo.
     *
     * @param target The target.
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Retrieves the creation timestamp of the uploaded photo.
     *
     * @return The creation timestamp.
     */
    public Long getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the uploaded photo.
     *
     * @param createdAt The creation timestamp.
     */
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the user who uploaded the photo.
     *
     * @return The user's username.
     */
    public String getFromUser() {
        return fromUser;
    }

    /**
     * Sets the user who uploaded the photo.
     *
     * @param fromUser The user's username.
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}
