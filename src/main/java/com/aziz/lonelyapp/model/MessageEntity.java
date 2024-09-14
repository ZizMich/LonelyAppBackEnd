package com.aziz.lonelyapp.model;

import jakarta.persistence.*;


import java.util.Date;


@Entity
@Table(name = "direct_messages")
public class MessageEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "sender")
    private String from;
    @Column(name = "receiver")
    private String to;
    @Column(name = "message")
    private String message;
    @Column(name = "sentdate")
    private Long sentdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSentdate() {
        return sentdate;
    }

    public void setSentdate(Long sentdate) {
        this.sentdate = sentdate;
    }
}