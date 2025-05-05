package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.PasteSaveDTO;
import com.paste_bin_clone.other.ACCESS_LEVEL;
import com.paste_bin_clone.other.LIFETIME;
import com.paste_bin_clone.services.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("paste")
public class PasteController extends CommonController {

    @Autowired
    private PasteService pasteService;

    @PostMapping("/save")
    public PasteDTO save(@RequestBody PasteSaveDTO pasteDTO) {
        return pasteService.savePaste(pasteDTO, getUser());
    }

    @GetMapping("")
    public List<PasteDTO> get() {
        return pasteService.getLastTenPastes();
    }

    @GetMapping("/{hashCode}")
    public PasteDTO getByHashCode(@PathVariable String hashCode) {
        return pasteService.getPasteByHashCode(hashCode);
    }

    @PostMapping("/addComment")
    public CommentDTO addComment(@RequestParam  Long pasteId,@RequestParam  String text) {
        return pasteService.addComment(pasteId, text, getUser());
    }

    @GetMapping("/search/{str}")
    public List<PasteDTO> searchPaste(@PathVariable String str) {
        return pasteService.searchPastes(str);
    }

    @GetMapping("/lifeTimeList")
    public Map<LIFETIME, String> getLifeTimes() {
        return pasteService.getLifeTimeList();
    }

    @GetMapping("/accessList")
    public Map<ACCESS_LEVEL, Map<String, String>> getAccessLevels() {
        return pasteService.getAccessList();
    }

}

