package com.aziz.lonelyapp.dto;





public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private long userid;


    public AuthResponseDTO(String accessToken, String refreshtoken, long userid) {
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

    public long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}