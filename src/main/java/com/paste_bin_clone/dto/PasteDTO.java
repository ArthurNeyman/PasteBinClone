package com.paste_bin_clone.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PasteDTO extends PasteSaveDTO {

    private long id;

    private String hashCode;

    private LocalDateTime dateCreate;

    private LocalDateTime deadTime;

    private List<CommentDTO> comments;

    private UserDTO user;

}
