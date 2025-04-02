package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.UserDTO;

import java.util.List;

@Deprecated
public interface IUserService {

    UserDTO registration(UserDTO user);

    UserDTO findByUserName(String userName);

    UserDTO getUser();

    List<PasteDTO>  getPastes();


    boolean changProfile(UserDTO user);
}
