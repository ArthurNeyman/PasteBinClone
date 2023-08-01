package com.paste_bin_clone.security;

import com.paste_bin_clone.entities.UserEntity;
import com.paste_bin_clone.services.IMapperService;
import com.paste_bin_clone.services.impl.UserService;
import com.paste_bin_clone.security.jwt.JWTUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private IMapperService mapperService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = mapperService.toEntity(userService.findByUserName(s), UserEntity.class);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + s + " not found");
        }
        return JWTUserFactory.create(user);
    }
}
