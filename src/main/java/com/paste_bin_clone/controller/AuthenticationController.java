package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.AuthenticationResponseDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API для аутентификации и регистрации")
public class AuthenticationController extends CommonController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Аутентификация пользователя", description = "Вход в систему с email и паролем")
    @ApiResponses({
        @ApiResponse(responseCode  = "200", description = "Successfully authenticated"),
        @ApiResponse(responseCode  = "400", description = "Bad request"),
        @ApiResponse(responseCode  = "401", description = "Invalid credentials")
    })
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO requestDTO) {
        return ResponseEntity.ok(authenticationService.login(requestDTO));
    }

    @PostMapping("/registration")
    @Operation(summary = "Регистрация пользователя", description = "Создание нового аккаунта")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully registered"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<AuthenticationResponseDTO> registration(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authenticationService.registration(userDTO));
    }

}
