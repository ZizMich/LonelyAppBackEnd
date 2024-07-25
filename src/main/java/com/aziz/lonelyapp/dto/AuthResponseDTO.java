package com.aziz.lonelyapp.dto;





public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;


    public AuthResponseDTO(String accessToken, String refreshtoken) {
        this.accessToken = accessToken;
        this.refreshToken=refreshtoken;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}