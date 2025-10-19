package com.paste_bin_clone.dto;

import com.paste_bin_clone.other.AccessLevel;
import com.paste_bin_clone.other.LifeTime;
import lombok.Data;

@Data
public class PasteSaveDTO {
    private long id;
    private String name;
    private String description;
    private AccessLevel access;
    private LifeTime lifeTime;
}
