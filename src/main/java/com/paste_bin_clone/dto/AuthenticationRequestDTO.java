package com.paste_bin_clone.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;


@Data
@Accessors(chain = true)
public class AuthenticationRequestDTO {
    @NotBlank(message = "Username is required")
    private String userName;
    @NotBlank(message = "Password is required")
    private String password;
}
