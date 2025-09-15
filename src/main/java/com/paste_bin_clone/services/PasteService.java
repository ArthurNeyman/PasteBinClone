package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.PasteSaveDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.entities.CommentEntity;
import com.paste_bin_clone.entities.PasteEntity;
import com.paste_bin_clone.other.ACCESS_LEVEL;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.other.LIFETIME;
import com.paste_bin_clone.repositories.CommentRepository;
import com.paste_bin_clone.repositories.PasteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PasteService extends CommonService {

    private final PasteRepository pasteRepository;
    private final CommentRepository commentRepository;
    private final UtilService utilService;

    public Map<LIFETIME, String> getLifeTimeList() {
        return LIFETIME.getLifTimes();
    }

    public Map<ACCESS_LEVEL, Map<String, String>> getAccessList() {
        return ACCESS_LEVEL.getAccessLevelList();
    }

    public List<PasteDTO> getLastTenPastes() {
        List<PasteEntity> listEntities =
                pasteRepository.findFirst10ByAccessAndDeadTimeAfterOrderByDateCreate(
                        ACCESS_LEVEL.PUBLIC.toString(), ZonedDateTime.now().toInstant()
                );
        return convertList(listEntities, (el) -> convertTo(el, PasteDTO.class));
    }

    public PasteDTO savePaste(PasteSaveDTO pasteSaveDTO, UserDTO userDTO) {

        PasteDTO pasteDTO = new PasteDTO(pasteSaveDTO);

        checkValidPaste(pasteDTO);

        ZonedDateTime now = ZonedDateTime.now();

        pasteDTO.setDateCreate(now.toInstant());
        pasteDTO.setDeadTime(now.plusMinutes(pasteDTO.getLifetime().getMinutes()).toInstant());

        pasteDTO.setHashCode(utilService.getHashCode());
        pasteDTO.setUser(userDTO);

        PasteEntity entity = pasteRepository.save(convertTo(pasteDTO, PasteEntity.class));

        pasteDTO.setId(entity.getId());

        return pasteDTO;
    }

    public PasteDTO getPasteByHashCode(String hashCod) {
        PasteEntity paste =
                pasteRepository.findByHashCodeAndDeadTimeAfterAndAccessIn(
                        hashCod,
                        ZonedDateTime.now().toInstant()
                        , new ArrayList<>(List.of(ACCESS_LEVEL.PUBLIC.toString(), ACCESS_LEVEL.UNLISTED.toString())));
        return convertTo(paste, PasteDTO.class);
    }

    public CommentDTO addComment(long pasteId, String commentText, UserDTO user) {
        CommentDTO commentDTO = new CommentDTO(pasteId, commentText, user == null ? 0 : user.getUserId());
        CommentEntity commentToSave = commentRepository.save(convertTo(commentDTO, CommentEntity.class));
        commentDTO.setId(commentToSave.getId());
        return commentDTO;
    }

    public void deleteByHashCode(String hashCode, UserDTO userDTO) {
        pasteRepository.deleteByHashCodeAndUserId(hashCode, userDTO.getUserId());
    }

    public List<PasteDTO> searchPastes(String searchString) {

        List<PasteDTO> dtos = new ArrayList<>();
        pasteRepository
                .findByNameIgnoreCaseContainingAndDeadTimeAfterOrDescriptionIgnoreCaseContainingAndDeadTimeAfter(
                        searchString, ZonedDateTime.now().toInstant(), searchString, ZonedDateTime.now().toInstant())
                .forEach(el -> dtos.add(convertTo(el, PasteDTO.class)));

        return dtos;
    }

    private void checkValidPaste(PasteDTO pasteDTO) throws ApplicationError {

        ApplicationError error = new ApplicationError();

        ERRORS errorEnumValue = ERRORS.EMPTY_REQUIRED_FIELD;

        if (pasteDTO.getName() == null || pasteDTO.getName().isEmpty()) {
            error.add(errorEnumValue, "name");
        }
        if (pasteDTO.getDescription() == null || pasteDTO.getDescription().isEmpty()) {
            error.add(errorEnumValue, "description");
        }
        if (pasteDTO.getLifetime() == null) {
            error.add(errorEnumValue, "lifetime");
        }
        if (pasteDTO.getAccess() == null) {
            error.add(errorEnumValue, "access");
        }
        if (!error.getErrors().isEmpty()) {
            throw error;
        }
    }
}
