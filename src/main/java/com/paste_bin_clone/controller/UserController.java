package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.services.PasteService;
import com.paste_bin_clone.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController extends CommonController {

    private final PasteService pasteService;
    private final UserService userService;

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
