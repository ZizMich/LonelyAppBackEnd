package com.aziz.lonelyapp.dto;

/**
 * Data Transfer Object for login requests.
 * Contains user's username and password.
 */
public class LoginDto {

    /**
     * User's username.
     */
    private String username;

    /**
     * User's password.
     */
    private String password;

    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
