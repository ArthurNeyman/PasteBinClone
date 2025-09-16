package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.AuthenticationRequestAnswerDTO;
import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public AuthenticationRequestAnswerDTO login(AuthenticationRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getUserName(),
                            requestDTO.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new ApplicationError().add(ERRORS.WRONG_USER_NAME_OR_PASSWORD, "");
        }
        UserDTO user = userService.findByUserName(requestDTO.getUserName());
        return new AuthenticationRequestAnswerDTO()
                .setUserDTO(user)
                .setToken(jwtTokenProvider.createToken(requestDTO.getUserName(), user.getRole()));
    }
    @Transactional
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
