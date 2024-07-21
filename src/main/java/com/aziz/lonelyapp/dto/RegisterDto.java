package com.aziz.lonelyapp.dto;

/**
 * This class is used to represent a user registration request
 * 
 * @author Aziz Seyidov
 */
public class RegisterDto {
    /**
     * The username of the user
     */
    private String username;
    /**
     * The email address of the user
     */
    private String email;
    /**
     * The password of the user
     */
    private String password;

    /**
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
