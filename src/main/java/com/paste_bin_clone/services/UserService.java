package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.entities.UserEntity;
import com.paste_bin_clone.other.ROLES;
import com.paste_bin_clone.repositories.PasteRepository;
import com.paste_bin_clone.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasteRepository pasteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MapperService mapper;

    public static final ConcurrentHashMap<String, UserDTO> USERS = new ConcurrentHashMap<>();

    public UserDTO registration(UserDTO user) {

        UserEntity userEntity = mapper.toEntity(user, UserEntity.class, user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(ROLES.USER.toString());
        userEntity = userRepository.save(userEntity);
        UserDTO userAnswer = (UserDTO) mapper.toDTO(userEntity, user);
        USERS.put(userAnswer.getUserName(), userAnswer);
        return userAnswer;


    }


    public UserDTO findByUserName(String userName) {
        UserDTO user = (UserDTO) mapper.toDTO(userRepository.findByUserName(userName), null);
        if (user != null) user.setPassword(userRepository.findByUserName(userName).getPassword());
        return user;
    }


    public List<PasteDTO> getPastes(UserDTO user) {
        List<PasteDTO> pasteUserList = new ArrayList<>();
        pasteRepository
                .findByUser(userRepository.findByUserName(user.getUserName()))
                .forEach(paste -> pasteUserList.add((PasteDTO) mapper.toDTO(paste, user)));
        return pasteUserList;
    }

    public boolean changeProfile(UserDTO newDataUser) {

        UserDTO oldUser = this.getUser(newDataUser.getUserName());

        if (!oldUser.getUserName().equals(newDataUser.getUserName()))
            if (userRepository.findByUserName(newDataUser.getUserName()) != null)
                return false;

        oldUser.setUserName(newDataUser.getUserName());
        oldUser.setEmail(newDataUser.getEmail());
        oldUser.setFirstName(newDataUser.getFirstName());
        oldUser.setLastName(newDataUser.getLastName());

        userRepository.save(mapper.toEntity(oldUser, UserEntity.class, newDataUser));

        return true;
    }

    public UserDTO getUser(String userName) {
        return USERS.get(userName);
    }

}
