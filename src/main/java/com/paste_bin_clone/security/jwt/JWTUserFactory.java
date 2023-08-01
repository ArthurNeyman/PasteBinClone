package com.paste_bin_clone.security.jwt;

import com.paste_bin_clone.entities.Role;
import com.paste_bin_clone.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JWTUserFactory {
    public JWTUserFactory() {
    }

    public static JWTUser create(UserEntity user){
        return new JWTUser(
            user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                mapToGrantedAuthorityList(user.getRoles()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorityList(List<Role> userRoles){
        return userRoles.stream()
                .map(role->
                    new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
