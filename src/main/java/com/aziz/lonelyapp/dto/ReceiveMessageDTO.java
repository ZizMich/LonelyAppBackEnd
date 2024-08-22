package com.aziz.lonelyapp.dto;

import java.util.Date;

public class ReceiveMessageDTO {
    private String sender;

    private String text;
    private Date sentat;

    public ReceiveMessageDTO(String sender, String text, Date sentat) {
        this.sentat = sentat;
        this.sender = sender;
        this.text = text;
    }



    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSentat() {
        return sentat;
    }

    public void setSentat(Date sentat) {
        this.sentat = sentat;
    }
}
