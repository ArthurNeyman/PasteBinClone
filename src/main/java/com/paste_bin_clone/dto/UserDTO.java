package com.paste_bin_clone.dto;

import com.paste_bin_clone.other.ROLES;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDTO {
    private Long userId;
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private ROLES role;
    private String email;
}
