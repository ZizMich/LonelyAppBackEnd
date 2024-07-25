package com.aziz.lonelyapp.dto;





public class AuthResponseDTO {
    private String accessToken;
    private String refresh_token;


    public AuthResponseDTO(String accessToken, String refreshtoken) {
        this.accessToken = accessToken;
        this.refresh_token=refreshtoken;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}