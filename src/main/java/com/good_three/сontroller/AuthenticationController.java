package com.good_three.сontroller;

import com.good_three.dto.AuthenticationRequestDTO;
import com.good_three.dto.ResponseStatusDTO;
import com.good_three.dto.UserDTO;
import com.good_three.security.jwt.JwtTokenProvider;
import com.good_three.services.IMapperService;
import com.good_three.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMapperService mapper;

    @Autowired
    private  JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseStatusDTO<Map<String, String>> login(@RequestBody AuthenticationRequestDTO requestDTO) {

        ResponseStatusDTO<Map<String, String> > res=new ResponseStatusDTO<Map<String, String> >();

        try {

            UserDTO user = userService.findByUserName(requestDTO.getUserName());

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUserName(), requestDTO.getPassword()));

            Map<String, String> response = new HashMap<>();
            response.put("token", jwtTokenProvider.createToken(requestDTO.getUserName(), user.getRoles()));

            res.setData(response);
            res.addMessage("Авторизация прошла успешно");

        } catch (AuthenticationException e) {
            res.setStatus(HttpStatus.BAD_GATEWAY);
            res.addMessage(e.getMessage());
        }

        return res;
    }

    @PostMapping("/registration")
    public ResponseStatusDTO<Map<String, String>> registration(@RequestBody UserDTO user) {

        ResponseStatusDTO<Map<String , String >> res = new ResponseStatusDTO<Map<String, String>>();

        try {

            if(userService.findByUserName(user.getUsername())!=null)
                throw new Exception("Пользователь с таким именем уже существует");

            UserDTO newUser = userService.registration(user);
            String token = jwtTokenProvider.createToken(newUser.getUsername(), newUser.getRoles());

            Map<String, String> response = new HashMap<String, String>();
            response.put("token", token);

            res.setData(response);
            res.addMessage("Регистрация прошла успешно");

        } catch (Exception e) {
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.addMessage(e.getMessage());
        }

        return res;
    }


}
