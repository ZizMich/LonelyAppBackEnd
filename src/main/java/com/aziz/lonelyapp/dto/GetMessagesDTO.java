package com.aziz.lonelyapp.dto;

public class GetMessagesDTO {
    private String chatId;

    private long start;

    private int limit;

    public GetMessagesDTO() {
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }




    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
