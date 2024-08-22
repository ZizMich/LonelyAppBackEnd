package com.aziz.lonelyapp.model;

import jakarta.persistence.*;


import java.util.Date;


@Entity
@Table(name = "direct_messages")
public class MessageEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="sender")
    private Long from;
    @Column(name = "receiver")
    private Long to;
    @Column(name = "message")
    private String message;
    @Column(name = "sentdate")
    private Date sentdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSentdate() {
        return sentdate;
    }

    public void setSentdate(Date sentdate) {
        this.sentdate = sentdate;
    }
}
