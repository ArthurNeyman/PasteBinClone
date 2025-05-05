package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.AuthenticationRequestAnswerDTO;
import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRequestAnswerDTO login(AuthenticationRequestDTO requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUserName(),
                        requestDTO.getPassword()
                )
        );
        UserDTO user = userService.findByUserName(requestDTO.getUserName());
        return new AuthenticationRequestAnswerDTO()
                .setUserDTO(user)
                .setToken(jwtTokenProvider.createToken(requestDTO.getUserName(), user.getRole()));
    }

    public AuthenticationRequestAnswerDTO registration(UserDTO user) {

        if (userService.usernameExist(user.getUserName())) {
            throw new ApplicationError().add(ERRORS.USER_NAME_ALREADY_EXIST, user.getUserName());
        }

        UserDTO newUser = userService.registration(user);

        return new AuthenticationRequestAnswerDTO()
                .setUserDTO(newUser)
                .setToken(jwtTokenProvider.createToken(newUser.getUserName(), newUser.getRole()));
    }
}
