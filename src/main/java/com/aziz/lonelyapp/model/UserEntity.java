package com.aziz.lonelyapp.model;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the database.
 */
@Entity
@Table(name = "users")
public class UserEntity {
    /**
     * The unique identifier of the user.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * The email of the user.
     */
    @Column(name = "email")
    private String email;

    /**
     * The password of the user.
     */
    @Column(name = "password")
    private String password;

    /**
     * The name of the user.
     */
    @Column(name = "name")
    private String name;

    /**
     * The roles of the user.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "uroles", joinColumns = @JoinColumn(name = "uid", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @Column(name = "avatar")
    private Long avatar;

    /**
     * Get the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the user.
     *
     * @param email The email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the unique identifier of the user.
     *
     * @return The unique identifier of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the unique identifier of the user.
     *
     * @param id The unique identifier of the user.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the user.
     *
     * @param name The name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the roles of the user.
     *
     * @return The roles of the user.
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Set the roles of the user.
     *
     * @param roles The roles of the user.
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getAvatar() {
        return avatar;
    }

    public void setAvatar(Long avatar) {
        this.avatar = avatar;
    }
}
