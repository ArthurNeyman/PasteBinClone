package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.services.PasteService;
import com.paste_bin_clone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("user")
public class UserController extends CommonController {

    @Autowired
    private PasteService pasteService;
    @Autowired
    private UserService userService;

    @GetMapping("/pastes")
    public List<PasteDTO> getPastes() {
        return userService.getPastes(getUser());
    }

    @PostMapping("/pastes/delete/{hashCode}")
    public void deletePaste(@PathVariable String hashCode) {
        pasteService.deleteByHashCode(hashCode, getUser());
    }

    @PostMapping("/pastes/save")
    public PasteDTO savePaste(@RequestBody PasteDTO pasteDTO) {
        return userService.updatePaste(pasteDTO, getUser());
    }
    //-----------------------------------------------------------

    //todo отладить изменение данных пользователя
    @PostMapping("/changeProfile")
    public UserDTO changeProfile(UserDTO user) {
        return user;
    }

    //------------------------------------------------------------------------------------------------------------------
}
