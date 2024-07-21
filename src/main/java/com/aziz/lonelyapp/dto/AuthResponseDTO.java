package com.aziz.lonelyapp.dto;

/**
 * Class for holding response of the authentication process.
 */
public class AuthResponseDTO {
    /**
     * Access token.
     */
    private String accessToken;
    /**
     * Token type. In our case, it's always Bearer.
     */
    private String tokenType = "Bearer ";

    /**
     * Constructor for AuthResponseDTO.
     * 
     * @param accessToken Access token for the user.
     */
    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns access token.
     * 
     * @return Access token.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets access token.
     * 
     * @param accessToken Access token.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns token type.
     * 
     * @return Token type.
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets token type.
     * 
     * @param tokenType Token type.
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
