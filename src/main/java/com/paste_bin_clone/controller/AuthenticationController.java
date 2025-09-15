package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.AuthenticationRequestAnswerDTO;
import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
public class AuthenticationController extends CommonController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationRequestAnswerDTO login(@Valid @RequestBody AuthenticationRequestDTO requestDTO) {
        return authenticationService.login(requestDTO);
    }

    @PostMapping("/registration")
    public AuthenticationRequestAnswerDTO registration(@Valid @RequestBody UserDTO userDTO) {
        return authenticationService.registration(userDTO);
    }


}
