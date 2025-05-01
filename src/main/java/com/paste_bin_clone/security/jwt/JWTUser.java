package com.paste_bin_clone.security.jwt;

import io.jsonwebtoken.lang.Collections;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class JWTUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final GrantedAuthority authority;

    public JWTUser(Long id, String username, String password, String firstName, String lastName, String email, GrantedAuthority authority) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.authority = authority;
    }

    @Override
    public Collection
            <? extends GrantedAuthority> getAuthorities() {
        return Collections.arrayToList(new GrantedAuthority[]{authority});
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
