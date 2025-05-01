package com.paste_bin_clone.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PasteDTO extends PasteSaveDTO {


    public PasteDTO(PasteSaveDTO pasteSaveDTO) {
        this.setName(pasteSaveDTO.getName());
        this.setDescription(pasteSaveDTO.getDescription());
        this.setAccess(pasteSaveDTO.getAccess());
        this.setLifetime(pasteSaveDTO.getLifetime());
    }

    public PasteDTO() {
    }

    private long id;

    private String hashCode;

    private LocalDateTime dateCreate;

    private LocalDateTime deadTime;

    private List<CommentDTO> comments;

    private UserDTO user;

}
