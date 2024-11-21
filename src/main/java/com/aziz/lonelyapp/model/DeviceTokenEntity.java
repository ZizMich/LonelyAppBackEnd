package com.aziz.lonelyapp.model;

import jakarta.persistence.*;
/**
 * This class represents a device token entity in the database.
 * It is used to store user device tokens for push notifications.
 */
@Entity
@Table(name = "device_tokens")
public class DeviceTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "device_token")
    private String deviceToken;

    /**
     * Returns the unique identifier of the device token entity.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the device token entity.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user identifier associated with the device token.
     *
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user identifier associated with the device token.
     *
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the device token string.
     *
     * @return the deviceToken
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * Sets the device token string.
     *
     * @param deviceToken the deviceToken to set
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
