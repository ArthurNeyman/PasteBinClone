package com.paste_bin_clone.services.impl;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.entities.UserEntity;
import com.paste_bin_clone.repositories.PasteRepository;
import com.paste_bin_clone.repositories.RoleRepository;
import com.paste_bin_clone.repositories.UserRepository;
import com.paste_bin_clone.security.jwt.JWTUser;
import com.paste_bin_clone.services.IMapperService;
import com.paste_bin_clone.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasteRepository pasteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Qualifier("mapperService")
    @Autowired
    private IMapperService mapper;

    @Override
    public UserDTO registration(UserDTO user) {

        try {

            UserEntity userEntity = mapper.toEntity(user, UserEntity.class);
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

            userEntity.setRoles(Collections.singletonList(roleRepository.findByName("USER")));

            userEntity = userRepository.save(userEntity);

            return (UserDTO) mapper.toDTO(userEntity);
        } catch (Exception e) {
            log.error("REGISTRATION ERROR : ", e);
            return new UserDTO();
        }

    }

    @Override
    public UserDTO findByUserName(String userName) {
        UserDTO user = (UserDTO) mapper.toDTO(userRepository.findByUserName(userName));
        if (user != null) user.setPassword(userRepository.findByUserName(userName).getPassword());
        return user;
    }

    @Override
    public List<PasteDTO> getPastes() {
        List<PasteDTO> pasteUserList = new ArrayList<>();
        pasteRepository.findByUser(userRepository.findByUserName(getUser().getUserName())).forEach(paste -> pasteUserList.add((PasteDTO) mapper.toDTO(paste)));
        return pasteUserList;
    }

    @Override
    public boolean changProfile(UserDTO newDataUser) {

        UserDTO oldUser = this.getUser();

        if (!oldUser.getUserName().equals(newDataUser.getUserName()))
            if (userRepository.findByUserName(newDataUser.getUserName()) != null)
                return false;

        oldUser.setUserName(newDataUser.getUserName());
        oldUser.setEmail(newDataUser.getEmail());
        oldUser.setFirstName(newDataUser.getFirstName());
        oldUser.setLastName(newDataUser.getLastName());

        userRepository.save(mapper.toEntity(oldUser, UserEntity.class));

        return true;
    }

    public UserDTO getUser() {

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            JWTUser user = (JWTUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return this.findByUserName(user.getUsername());
        }
        return null;
    }

}
