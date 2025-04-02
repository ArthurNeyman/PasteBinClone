package com.paste_bin_clone.dto;

import com.paste_bin_clone.entities.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserDTO {

    private Long userId;

    private String password;

    private String userName;

    private String firstName;

    private String lastName;

    private List<Role> roles;

    private String email;

}
