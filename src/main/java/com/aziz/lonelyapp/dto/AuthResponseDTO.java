package com.aziz.lonelyapp.dto;





public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String userid;


    public AuthResponseDTO(String accessToken, String refreshtoken, String userid) {
        this.accessToken = accessToken;
        this.refreshToken=refreshtoken;
        this.userid = userid;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}