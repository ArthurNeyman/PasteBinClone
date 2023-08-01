package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.ResponseStatusDTO;
import com.paste_bin_clone.services.IPasteService;
import com.paste_bin_clone.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("paste")
@Slf4j
public class PasteController {

    @Autowired
    private IPasteService pasteService;

    @Autowired
    private IUserService userService;

    @GetMapping("")//Получить последние 10 паст public
    public ResponseStatusDTO<List<PasteDTO>> get() {

        ResponseStatusDTO<List<PasteDTO>> res = new ResponseStatusDTO<>();

        try {
            res.setData(pasteService.getLastTenPastes());
            res.addMessage(("Получены послежние 10 паст из публичного доступа"));
        } catch (Exception e) {
            log.error("", e);
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.addMessage(("Ошибка на стороне сервера"));
        }
        return res;
    }

    @GetMapping("/{hashCode}")//Получение пасту по Хэшкоду
    public ResponseStatusDTO getByHashCode(@PathVariable String hashCode) {

        ResponseStatusDTO res = new ResponseStatusDTO();

        try {
            res.setData(pasteService.getPasteByHashCode(hashCode));
            if (res.getData() == null) {
                res.setStatus(HttpStatus.NO_CONTENT);
                res.addMessage(("Пасты с таким хэшкодом не существует или ее срок жизни истек"));
            } else {
                res.addMessage(("Паста получена"));
            }
        } catch (Exception e) {
            log.error("", e);
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.addMessage(("Ошибка на стороне сервера"));
        }
        return res;
    }

    @PostMapping("/publication")//Публикация пасты
    public ResponseStatusDTO save(@RequestBody PasteDTO pasteDTO) {

        ResponseStatusDTO res = new ResponseStatusDTO();

        try {

            pasteDTO.setUser(userService.getUser());

            if (pasteDTO.getId() == 0) {
                res.setData(pasteService.savePaste(pasteDTO));
                res.addMessage(("Паста опубликована"));
            } else {
                res.addMessage(("Паста не опубликована"));
            }

        } catch (Exception e) {
            log.error("", e);
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.addMessage(("Ошибка на стороне сервера"));
        }

        return res;

    }

    @PostMapping("/comment")//добавление комментария к пасте
    public ResponseStatusDTO addComment(@RequestBody CommentDTO commentDTO) {

        ResponseStatusDTO res = new ResponseStatusDTO();

        commentDTO.setUserName(userService.getUser() != null ? userService.getUser().getUserName() : null);

        if (pasteService.saveComment(commentDTO))
            res.addMessage("Ваш комментарий добавлен");
        else
            res.addMessage("Ваш комментарий не добавлен");

        return res;
    }

    @GetMapping("/search/{str}") //поиск паст по коду или названию
    public ResponseStatusDTO searchPaste(@PathVariable String str) {

        ResponseStatusDTO res = new ResponseStatusDTO();

        List<PasteDTO> dtos = pasteService.searchPastes(str);

        res.setData(dtos);

        if (dtos.size() == 0)
            res.addMessage("Пасты по запросу не обнаружены");
        else
            res.addMessage("Найдено" + dtos.size() + " по запросу");

        return res;
    }


}

