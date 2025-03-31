package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.AccessDTO;
import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.other.LIFETIME;

import java.util.List;
import java.util.Map;

public interface IPasteService {

    Map<LIFETIME, String> getLifeTimeList();

    List<AccessDTO> getAccessList();

    List<PasteDTO> getLastTenPastes();

    PasteDTO savePaste(PasteDTO pasteDTO);

    PasteDTO getPaste(String hashCode);

    PasteDTO getPasteByHashCode(String hashCod);

    boolean saveComment(CommentDTO commentDTO);

    List<PasteDTO> getByUserName(String userName);

    boolean deleteByHashCode(String hashCode);

    List<PasteDTO> searchPastes(String searchString);
}
