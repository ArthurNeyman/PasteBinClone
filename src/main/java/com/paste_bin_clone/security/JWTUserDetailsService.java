package com.paste_bin_clone.security;

import com.paste_bin_clone.entities.UserEntity;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.security.jwt.JWTUserFactory;
import com.paste_bin_clone.services.CommonService;
import com.paste_bin_clone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailsService extends CommonService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userDb = userService.findEntityByUserName(userName);
        if (userDb == null) {
            throw new ApplicationError().add(ERRORS.DOES_NOT_EXIST, userName);
        }
        return JWTUserFactory.create(userDb);
    }
}
