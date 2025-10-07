package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.AuthenticationResponseDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public AuthenticationResponseDTO login(AuthenticationRequestDTO requestDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                requestDTO.getUserName(),
                requestDTO.getPassword()
            )
        );
        UserDTO user = userService.findByUserName(requestDTO.getUserName());
        return new AuthenticationResponseDTO()
            .setUserDTO(user)
            .setToken(jwtTokenProvider.createToken(requestDTO.getUserName(), user.getRole()));
    }

    @Transactional
    public AuthenticationResponseDTO registration(UserDTO user) {
        if (userService.usernameExist(user.getUserName())) {
            throw new ApplicationError()
                .add(ERRORS.USER_NAME_ALREADY_EXIST, user.getUserName())
                .withStatus(HttpStatus.CONFLICT);
        }
        UserDTO newUser = userService.registration(user);
        return new AuthenticationResponseDTO()
            .setUserDTO(newUser)
            .setToken(jwtTokenProvider.createToken(newUser.getUserName(), newUser.getRole()));
    }
}
