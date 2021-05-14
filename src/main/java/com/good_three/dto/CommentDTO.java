package com.good_three.dto;

public class CommentDTO {

    private long id;
    private long pasteId;
    private String text;
    private String userName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getPasteId() {
        return pasteId;
    }

    public void setPasteId(long pasteId) {
        this.pasteId = pasteId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
