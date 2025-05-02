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
public class UserService extends CommonService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasteRepository pasteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static final ConcurrentHashMap<String, UserDTO> USERS = new ConcurrentHashMap<>();

    public UserDTO registration(UserDTO user) {
        UserEntity userEntity = this.convertTo(user, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(ROLES.USER.toString());
        userEntity = userRepository.save(userEntity);
        UserDTO userAnswer = this.convertTo(userEntity, UserDTO.class);
        USERS.put(userAnswer.getUserName(), userAnswer);
        return userAnswer;
    }

    public boolean usernameExist(String userName) {
        return userRepository.userNameExist(userName);
    }

    public UserDTO findByUserName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName);
        return convertTo(userEntity, UserDTO.class);
    }

    public UserEntity findEntityByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<PasteDTO> getPastes(UserDTO user) {
        List<PasteDTO> pasteUserList = new ArrayList<>();
        pasteRepository
                .findAllByUserId(user.getUserId())
                .forEach(paste -> pasteUserList.add(convertTo(paste, PasteDTO.class)));
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

        userRepository.save(convertTo(oldUser, UserEntity.class));

        return true;
    }

    public UserDTO getUser(String userName) {
        return USERS.computeIfAbsent(userName, val -> convertTo(userRepository.findByUserName(userName), UserDTO.class));
    }

}
