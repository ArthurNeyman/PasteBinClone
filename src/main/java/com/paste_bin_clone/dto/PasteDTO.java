package com.paste_bin_clone.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class PasteDTO {

    private long id;

    private String hashCode;

    private LocalDateTime dateCreate;

    private String name;

    private long accessId;

    private long lifetimeId;

    private LocalDateTime deadTime;

    private String description;

    private List<CommentDTO> comments;

    private UserDTO user;
}
