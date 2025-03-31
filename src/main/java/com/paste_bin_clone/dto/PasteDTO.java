package com.paste_bin_clone.dto;

import com.paste_bin_clone.other.ACCESS_LEVEL;
import com.paste_bin_clone.other.LIFETIME;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PasteDTO {

    private long id;

    private String hashCode;

    private LocalDateTime dateCreate;

    private String name;

    private ACCESS_LEVEL access;

    private LIFETIME lifetime;

    private LocalDateTime deadTime;

    private String description;

    private List<CommentDTO> comments;

    private UserDTO user;

}
