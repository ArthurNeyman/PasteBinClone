package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.services.PasteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Доступно авторизированным пользователям
@RestController
@RequestMapping("user")
@CrossOrigin
@Slf4j
public class UserController extends CommonController {

    @Autowired
    private PasteService pasteService;

    @GetMapping("/pastes")//получить пасты пользователя
    public List<PasteDTO> getPastes() {
        return pasteService.getByUser(getUser());
    }

    @PostMapping("/pastes/delete/{hashCode}")//удалить пасту
    public void deletePaste(@PathVariable String hashCode) {
        pasteService.deleteByHashCode(hashCode, getUser());
    }

    @PostMapping("/pastes/save")//Изменить пасту
    public PasteDTO savePaste(@RequestBody PasteDTO pasteDTO) {
        return pasteService.savePaste(pasteDTO, getUser());
    }
    //-----------------------------------------------------------

    //todo отладить изменение данных пользователя
    @PostMapping("/change") //изменить профиль
    public UserDTO changeProfile(UserDTO user) {
        return user;
    }

    //------------------------------------------------------------------------------------------------------------------
}
