package com.good_three.security;

import com.good_three.entities.UserEntity;
import com.good_three.services.IMapperService;
import com.good_three.services.impl.UserService;
import com.good_three.security.jwt.JWTUserFactory;
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
        UserEntity user= (UserEntity) mapperService.toEntity(userService.findByUserName(s));
         if(user==null){
             throw new UsernameNotFoundException("User with username "+s+" not found");
         }
        return JWTUserFactory.create(user);
    }
}
