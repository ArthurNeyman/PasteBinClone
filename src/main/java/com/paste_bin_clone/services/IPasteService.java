package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.*;
import com.paste_bin_clone.other.LIFETIME;

import java.util.List;
import java.util.Map;


@Deprecated
public interface IPasteService {

    Map<LIFETIME, String> getLifeTimeList();

    List<AccessDTO> getAccessList();

    List<PasteDTO> getLastTenPastes();

    PasteDTO savePaste(PasteSaveDTO pasteSaveDTO, UserDTO userDTO);

    PasteDTO getPaste(String hashCode);

    PasteDTO getPasteByHashCode(String hashCod);

    CommentDTO saveComment(CommentDTO commentDTO);

    List<PasteDTO> getByUserName(String userName);

    boolean deleteByHashCode(String hashCode);

    List<PasteDTO> searchPastes(String searchString);
}
