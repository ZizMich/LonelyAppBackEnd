package com.aziz.lonelyapp.dto;

public class SendMessageDTO {
    private String receiver;

    private String text;

    public SendMessageDTO( String receiver, String text) {
        this.receiver = receiver;
        this.text = text;
    }





    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
