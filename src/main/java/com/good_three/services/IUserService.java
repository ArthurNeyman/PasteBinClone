package com.good_three.services;

import com.good_three.dto.PasteDTO;
import com.good_three.dto.UserDTO;
import com.good_three.entities.UserEntity;

import java.util.List;

public interface IUserService {

    UserDTO registration(UserDTO user);

    UserDTO findByUserName(String userName);

    UserDTO getUser();

    List<PasteDTO>  getPastes();


    boolean changProfile(UserDTO user);
}
