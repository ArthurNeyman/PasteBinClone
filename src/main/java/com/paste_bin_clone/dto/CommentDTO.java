package com.paste_bin_clone.dto;


import lombok.Data;

@Data
public class CommentDTO {
    private long id;
    private long pasteId;
    private String text;
    private long userId;

    public CommentDTO() {
    }

    public CommentDTO(long pasteId, String text, long userId) {
        this.pasteId = pasteId;
        this.text = text;
        this.userId = userId;
    }
}
