package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.AuthenticationRequestAnswerDTO;
import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.ResponseStatusDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.security.jwt.JwtTokenProvider;
import com.paste_bin_clone.services.IUserService;
import com.paste_bin_clone.services.impl.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "auth")
@CrossOrigin
public class AuthenticationController {


    @Autowired
    private AuthenticationService authenticationService;

    @Operation(
            summary = "Login ",
            description = "Get "

    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseStatusDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "The Tutorial with given Id was not found.", content = {@Content(schema = @Schema())})
    })
    @PostMapping("/login")
    public ResponseStatusDTO<AuthenticationRequestAnswerDTO> login(@RequestBody AuthenticationRequestDTO requestDTO) {
        return authenticationService.login(requestDTO);
    }


    @PostMapping("/registration")
    public ResponseStatusDTO<AuthenticationRequestAnswerDTO> registration(@RequestBody UserDTO userDTO) {
        return authenticationService.registration(userDTO);
    }


}
