package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.AccessDTO;
import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.LifeTimeDTO;
import com.paste_bin_clone.dto.PasteDTO;

import java.util.List;

public interface IPasteService {

    List<LifeTimeDTO> getLifeTimeList();

    List<AccessDTO> getAccessList();

    List<PasteDTO> getLastTenPastes();

    PasteDTO savePaste(PasteDTO pasteDTO);

    PasteDTO getPaste(String hashCod);

    PasteDTO getPasteByHashCode(String hashCod);

    boolean saveComment(CommentDTO commentDTO);

    List<PasteDTO> getByUserName(String userName);

    boolean deleteByHashCode(String hashCode);

    List<PasteDTO> searchPastes(String searchString);
}
