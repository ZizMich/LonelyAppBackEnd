package com.aziz.lonelyapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

/**
 * This class represents a Refresh Token entity used for authentication and authorization.
 * It is mapped to the "refreshTokens" table in the database.
 */
@Entity
@Table(name = "refreshTokens")
public class RefreshToken {

    /**
     * Unique identifier for the refresh token.
     */
    @Id
    @Column(name = "uid")
    private String id;

    /**
     * The actual refresh token string.
     */
    @Column(name = "token")
    private String token;

    /**
     * The date and time when the refresh token was issued.
     */
    @Column(name = "issuedat")
    private Date issuedat;

    /**
     * The date and time when the refresh token will expire.
     */
    @Column(name = "expiredat")
    private Date expiredate;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getIssuedat() {
        return issuedat;
    }

    public void setIssuedat(Date issuedat) {
        this.issuedat = issuedat;
    }

    public Date getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(Date expiredate) {
        this.expiredate = expiredate;
    }
}