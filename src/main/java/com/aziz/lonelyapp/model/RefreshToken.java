package com.aziz.lonelyapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "refreshTokens")
public class RefreshToken {
    @Id
    @Column(name = "uid")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "issuedat")
    private Date issuedat;

    @Column(name = "expiredat")
    private Date expiredate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
