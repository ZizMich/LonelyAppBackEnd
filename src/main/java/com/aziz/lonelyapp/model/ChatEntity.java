package com.aziz.lonelyapp.model;

import jakarta.persistence.*;

/**
 * This class represents a chat entity in the application.
 * It is mapped to the "groups" table in the database using JPA annotations.
 */
@Entity
@Table(name = "groups")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private Long avatar;

    /**
     * Returns the unique identifier of the chat entity.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the chat entity.
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the name of the chat entity.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the chat entity.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the avatar of the chat entity.
     *
     * @return the avatar
     */
    public Long getAvatar() {
        return avatar;
    }

    /**
     * Sets the avatar of the chat entity.
     *
     * @param avatar the avatar to set
     */
    public void setAvatar(Long avatar) {
        this.avatar = avatar;
    }
}
