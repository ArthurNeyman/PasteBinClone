package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.PasteSaveDTO;
import com.paste_bin_clone.services.PasteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("paste")
@Slf4j
@CrossOrigin
public class PasteController extends CommonController {

    @Autowired
    private PasteService pasteService;

    @PostMapping("/savePaste")//Публикация пасты
    public PasteDTO save(@RequestBody PasteSaveDTO pasteDTO) {
        return pasteService.savePaste(pasteDTO, getUser());
    }

    @GetMapping("")//Получить последние 10 паст public
    public List<PasteDTO> get() {
        return pasteService.getLastTenPastes(null);
    }

    @GetMapping("/{hashCode}")//Получение пасту по Хэшкоду
    public PasteDTO getByHashCode(@PathVariable String hashCode) {
        return pasteService.getPasteByHashCode(hashCode);
    }


    @PostMapping("/addComment")//добавление комментария к пасте
    public CommentDTO addComment(long pasteId, String text) {
        return pasteService.saveComment(pasteId, text, getUser());
    }

    @GetMapping("/search/{str}") //поиск паст по коду или названию
    public List<PasteDTO> searchPaste(@PathVariable String str) {
        return pasteService.searchPastes(str);
    }

}

