package com.aziz.lonelyapp.model;

import jakarta.persistence.*;

/**
 * Represents a role in the application.
 *
 * @author Aziz
 * @since 1.0
 */
@Entity
@Table(name = "roles")
public class Role {

    /**
     * The unique identifier of the role.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the role.
     */
    @Column(name = "name")
    private String name;

    /**
     * Retrieves the name of the role.
     *
     * @return the name of the role
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     *
     * @param name the name of the role
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the unique identifier of the role.
     *
     * @return the unique identifier of the role
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the role.
     *
     * @param id the unique identifier of the role
     */
    public void setId(Long id) {
        this.id = id;
    }
}
