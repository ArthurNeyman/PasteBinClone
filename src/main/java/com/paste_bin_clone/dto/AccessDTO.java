package com.paste_bin_clone.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccessDTO {

    private long id;

    private String name;

}


