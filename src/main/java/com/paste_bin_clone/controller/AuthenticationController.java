package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.AuthenticationRequestAnswerDTO;
import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController extends CommonController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationRequestAnswerDTO login(@RequestBody AuthenticationRequestDTO requestDTO) {
        return authenticationService.login(requestDTO);
    }

    @PostMapping("/registration")
    public AuthenticationRequestAnswerDTO registration(@RequestBody UserDTO userDTO) {
        return authenticationService.registration(userDTO);
    }


}
