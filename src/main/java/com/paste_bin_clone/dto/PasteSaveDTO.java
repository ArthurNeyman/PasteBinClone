package com.paste_bin_clone.dto;

import com.paste_bin_clone.other.ACCESS_LEVEL;
import com.paste_bin_clone.other.LIFETIME;
import lombok.Data;

@Data
public class PasteSaveDTO {

    private long id;
    private String name;
    private String description;
    private ACCESS_LEVEL access;
    private LIFETIME lifetime;

}
