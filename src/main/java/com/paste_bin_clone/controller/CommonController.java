package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.security.jwt.JWTUser;
import com.paste_bin_clone.services.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class CommonController {

    @Autowired
    private UserService userService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest req, Exception ex) {
        if (ex instanceof ApplicationError) {
            ApplicationError error = (ApplicationError) ex;
            return buildErrorResponse(error.getErrors(), HttpStatus.BAD_REQUEST);
        }
        if (ex instanceof AuthenticationException) {
            Map<ERRORS, String> authError = Collections.singletonMap(
                ERRORS.WRONG_USER_NAME_OR_PASSWORD, "Invalid credentials"
            );
            return buildErrorResponse(authError, HttpStatus.UNAUTHORIZED);
        }
        Map<ERRORS, String> unknownError = Collections.singletonMap(
            ERRORS.UNKNOWN_ERROR, "An unexpected error"
        );
        return buildErrorResponse(unknownError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public UserDTO getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
            JWTUser user = (JWTUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.getUser(user.getUsername());
        }
        return null;
    }

    protected ResponseEntity<ErrorResponse> buildErrorResponse(Map<ERRORS, ?> errors, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(errors, status.value());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @Getter
    @RequiredArgsConstructor
    public static class ErrorResponse {

        private final Map<ERRORS, ?> errors;
        private final int status;
        private final long timestamp;

        public ErrorResponse(Map<ERRORS, ?> errors, int status) {
            this.errors = errors;
            this.status = status;
            this.timestamp = System.currentTimeMillis();
        }
    }
}
