package com.paste_bin_clone.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationResponseDTO {
    private String token;
    private UserDTO userDTO;
}
