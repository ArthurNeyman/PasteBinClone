package com.paste_bin_clone.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PasteDTO extends PasteSaveDTO {

    public PasteDTO() {
    }

    public PasteDTO(PasteSaveDTO pasteSaveDTO) {
        this.setName(pasteSaveDTO.getName());
        this.setDescription(pasteSaveDTO.getDescription());
        this.setAccess(pasteSaveDTO.getAccess());
        this.setLifetime(pasteSaveDTO.getLifetime());
        this.setId(pasteSaveDTO.getId());
    }

    private String hashCode;
    private Instant deadTime;
    private Instant dateCreate;
    private List<CommentDTO> comments = new ArrayList<>();
    private UserDTO user;
}
