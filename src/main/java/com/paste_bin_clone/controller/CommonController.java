package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.security.jwt.JWTUser;
import com.paste_bin_clone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class CommonController {

    @Autowired
    private UserService userService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(HttpServletRequest req, Exception ex) {

        if (ex instanceof ApplicationError) {
            ApplicationError error = (ApplicationError) ex;
            return toError(error.getErrors());
        }

        if (ex instanceof AuthenticationException) {
            return toError(Map.of(ERRORS.WRONG_USER_NAME_OR_PASSWORD, ""));
        }

        return toError(Map.of(ERRORS.UNKNOWN_ERROR, ""));
    }

    public UserDTO getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
            JWTUser user = (JWTUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.getUser(user.getUsername());
        }
        return null;
    }

    private ResponseEntity<String> toError(Map<ERRORS, ?> params) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(
                    new JSONObject()
                            .put("errors", new JSONObject(params))
                            .toString(),
                    headers, HttpStatus.BAD_REQUEST
            );
        } catch (JSONException jsonException) {
            log.error("JSON exception", jsonException);
        }
        return null;
    }
}
