package com.good_three.services.impl;

import com.good_three.dto.PasteDTO;
import com.good_three.dto.UserDTO;
import com.good_three.entities.Role;
import com.good_three.entities.UserEntity;
import com.good_three.repositories.PasteRepository;
import com.good_three.repositories.RoleRepository;
import com.good_three.repositories.UserRepository;
import com.good_three.security.jwt.JWTUser;
import com.good_three.services.IMapperService;
import com.good_three.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Autowired
    private IMapperService mapper;

    @Override
    public UserDTO registration(UserDTO user) {

        Role roleUser=roleRepository.findByName("USER");
        List<Role> userRoles=new ArrayList<>();
        userRoles.add(roleUser);

        UserEntity userEntity= (UserEntity) mapper.toEntity(user);

        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRoles(userRoles);

        return (UserDTO) mapper.toDTO(userRepository.save(userEntity));
    }

    @Override
    public UserDTO findByUserName(String userName) {
        UserDTO user= (UserDTO) mapper.toDTO(userRepository.findByUsername(userName));
        if(user!=null) user.setPassword(userRepository.findByUsername(userName).getPassword());
        return user;
    }

    @Override
    public List<PasteDTO> getPastes() {
        List<PasteDTO> pasteUserList=new ArrayList<>();
        pasteRepository.findByUser(userRepository.findByUsername(getUser().getUsername())).forEach(paste->{
            pasteUserList.add((PasteDTO) mapper.toDTO(paste));
        });
        return pasteUserList;
    }

    @Override
    public boolean changProfile(UserDTO newDataUser) {

        UserDTO oldUser=this.getUser();

        if(!oldUser.getUsername().equals(newDataUser.getUsername()))
            if (userRepository.findByUsername(newDataUser.getUsername())!=null)
                return  false;

            oldUser.setUsername(newDataUser.getUsername());
            oldUser.setEmail(newDataUser.getEmail());
            oldUser.setFirstName(newDataUser.getFirstName());
            oldUser.setLastName(newDataUser.getLastName());

            userRepository.save((UserEntity) mapper.toEntity(oldUser));

        return true;
    }

    public UserDTO getUser(){

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            JWTUser user = (JWTUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return this.findByUserName(user.getUsername());
        }
        return null;
    }

}
