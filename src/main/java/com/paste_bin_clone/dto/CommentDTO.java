package com.paste_bin_clone.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentDTO {

    private long id;

    private long pasteId;

    private String text;

    private String userName;
}
