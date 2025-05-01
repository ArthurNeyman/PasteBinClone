package com.paste_bin_clone.security.jwt;

import com.paste_bin_clone.entities.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JWTUserFactory {
    public JWTUserFactory() {
    }

    public static JWTUser create(UserEntity user) {
        return new JWTUser(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                new SimpleGrantedAuthority(user.getRole())
        );
    }
}
