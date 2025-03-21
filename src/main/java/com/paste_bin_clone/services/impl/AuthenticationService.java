package com.paste_bin_clone.services.impl;

import com.paste_bin_clone.dto.AuthenticationRequestAnswerDTO;
import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.ResponseStatusDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.security.jwt.JwtTokenProvider;
import com.paste_bin_clone.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseStatusDTO<AuthenticationRequestAnswerDTO> login(@RequestBody AuthenticationRequestDTO requestDTO) {

        ResponseStatusDTO<AuthenticationRequestAnswerDTO> res = new ResponseStatusDTO<>();

        try {
            UserDTO user = userService.findByUserName(requestDTO.getUserName());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUserName(), requestDTO.getPassword()));
            res.setData(
                    new AuthenticationRequestAnswerDTO()
                            .setUserDTO(user)
                            .setToken(jwtTokenProvider.createToken(requestDTO.getUserName(), user.getRoles()))
            );
            res.addMessage("Авторизация прошла успешно");
        } catch (AuthenticationException e) {
            res.setStatus(HttpStatus.FORBIDDEN);
        }

        return res;
    }


    public ResponseStatusDTO<AuthenticationRequestAnswerDTO> registration(@RequestBody UserDTO user) {
        ResponseStatusDTO<AuthenticationRequestAnswerDTO> res = new ResponseStatusDTO<>();
        try {
            if (userService.findByUserName(user.getUserName()) != null)
                throw new Exception("Пользователь с таким именем уже существует");
            UserDTO newUser = userService.registration(user);
            res.setData(
                    new AuthenticationRequestAnswerDTO()
                            .setUserDTO(user)
                            .setToken(jwtTokenProvider.createToken(newUser.getUserName(), newUser.getRoles()))
            );
            res.addMessage("Регистрация прошла успешно");
        } catch (Exception e) {
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.addMessage(e.getMessage());
        }
        return res;
    }
}
