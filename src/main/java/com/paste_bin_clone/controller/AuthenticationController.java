package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.AuthenticationResponseDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.services.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
@Api(tags = "Authentication")
public class AuthenticationController extends CommonController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ApiOperation("User authentication")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully authenticated"),
    })
    public AuthenticationResponseDTO login(@Valid @RequestBody AuthenticationRequestDTO requestDTO) {
        return authenticationService.login(requestDTO);
    }

    @PostMapping("/registration")
    @ApiOperation("User registration")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully registration"),
    })
    public AuthenticationResponseDTO registration(@Valid @RequestBody UserDTO userDTO) {
        return authenticationService.registration(userDTO);
    }


}
