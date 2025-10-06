package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.PasteSaveDTO;
import com.paste_bin_clone.other.AccessLevel;
import com.paste_bin_clone.other.LIFETIME;
import com.paste_bin_clone.services.PasteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("paste")
@RequiredArgsConstructor
public class PasteController extends CommonController {

    private final PasteService pasteService;

    @GetMapping()
    public List<PasteDTO> get() {
        return pasteService.getLastTenPastes();
    }

    @PostMapping("/save")
    public PasteDTO save(@RequestBody PasteSaveDTO pasteDTO) {
        return pasteService.savePaste(pasteDTO, getUser());
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
    public Map<AccessLevel, Map<String, String>> getAccessLevels() {
        return pasteService.getAccessList();
    }

}

