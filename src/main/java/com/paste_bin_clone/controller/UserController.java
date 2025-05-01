package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.ResponseStatusDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.security.jwt.JWTUser;
import com.paste_bin_clone.services.PasteService;
import com.paste_bin_clone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
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
    public ResponseStatusDTO<List<PasteDTO>> getPastes() {

        ResponseStatusDTO<List<PasteDTO>> res = new ResponseStatusDTO<>();

        try {
            if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
                JWTUser user = (JWTUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                res.setData(pasteService.getByUserName(user.getUsername()));
                res.addMessage("Пасты пользователя получены");
            }

        } catch (Exception e) {
            log.error("", e);
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.addMessage("Внутренняя ошибка сервера");
        }

        return res;
    }

    @PostMapping("/pastes/delete/{hashCode}")//удалить пасту
    @Transactional
    public ResponseStatusDTO<Null> deletePaste(@PathVariable String hashCode) {

        ResponseStatusDTO<Null> res = new ResponseStatusDTO<>();
        UserDTO user = getUser();
        PasteDTO paste = pasteService.getPaste(hashCode, user);

        try {
            if (user != null &&
                    user.getUserName().equals(paste.getUser().getUserName())) {
                pasteService.deleteByHashCode(hashCode);
                res.addMessage("Паста удалена");
            } else {
                res.setStatus(HttpStatus.FORBIDDEN);
                res.addMessage("Паста не удалена");
            }

        } catch (Exception e) {
            System.out.println(e);
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.addMessage("Возникла ошибка при удалении пасты");
        }

        return res;
    }

    @PostMapping("/pastes/save")//Изменить пасту
    public ResponseStatusDTO<PasteDTO> savePaste(@RequestBody PasteDTO pasteDTO) {

        ResponseStatusDTO<PasteDTO> res = new ResponseStatusDTO<>();

        try {
            res.setData(pasteService.savePaste(pasteDTO, getUser()));
            res.addMessage("Паста изменена");
        } catch (Exception e) {
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.addMessage("Ошибка при срхранении пасты");
        }

        return res;
    }
    //-----------------------------------------------------------

    @PostMapping("/change") //изменить профиль
    public ResponseStatusDTO<UserDTO> changeProfile(UserDTO user) {
        ResponseStatusDTO<UserDTO> res = new ResponseStatusDTO<>();
        try {
            res.setData(user);
            res.addMessage("Профиль успешно измененн");
        } catch (Exception e) {
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.addMessage("Ошибка при изменении профиля");
        }
        return res;
    }

    //------------------------------------------------------------------------------------------------------------------
}
