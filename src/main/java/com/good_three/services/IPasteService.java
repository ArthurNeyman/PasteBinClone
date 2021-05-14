package com.good_three.services;

import com.good_three.dto.AccessDTO;
import com.good_three.dto.CommentDTO;
import com.good_three.dto.LifeTimeDTO;
import com.good_three.dto.PasteDTO;

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
