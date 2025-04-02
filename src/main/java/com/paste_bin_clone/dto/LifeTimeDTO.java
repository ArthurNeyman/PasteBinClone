package com.paste_bin_clone.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Deprecated
public class LifeTimeDTO {

    private long id;

    private String name;

    private long minutes;
}

