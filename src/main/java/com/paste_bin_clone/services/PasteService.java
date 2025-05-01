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
import com.paste_bin_clone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class PasteService {

    @Autowired
    private PasteRepository pasteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MapperService mapper;

    //------------------------------------------------------------------------------------------------------------------

    //Получить список доступных вариантов времени жизни пасты
    public Map<LIFETIME, String> getLifeTimeList() {
        return LIFETIME.getLifTimes();
    }

    //Получить список вариантов доступа к пастам
    public List<ACCESS_LEVEL> getAccessList() {
        return List.of(ACCESS_LEVEL.values());
    }

    //получить 10 последних паст
    public List<PasteDTO> getLastTenPastes(UserDTO userDTO) {
        List<PasteDTO> list = new ArrayList<>();
        pasteRepository.findFirst10ByAccessAndDeadTimeAfterOrderByDateCreate(ACCESS_LEVEL.PUBLIC.toString(), LocalDateTime.now())
                .forEach(el -> list.add((PasteDTO) mapper.toDTO(el, userDTO)));
        return list;
    }

    //Сохранить пасту
    public PasteDTO savePaste(PasteSaveDTO pasteSaveDTO, UserDTO userDTO) {

        PasteDTO pasteDTO = (PasteDTO) pasteSaveDTO;

        checkValidPaste(pasteDTO);

        LocalDateTime time = LocalDateTime.now();

        pasteDTO.setDateCreate(time);

        pasteDTO.setDeadTime(time.plusMinutes(pasteDTO.getLifetime().getMinutes()));

        pasteDTO.setHashCode(getHashCode());
        pasteDTO.setUser(userDTO);

        PasteEntity entity = pasteRepository.save(mapper.toEntity(pasteDTO, PasteEntity.class, userDTO));
        pasteDTO.setId(entity.getId());

        return pasteDTO;
    }

    //Пoлучить пасту по Хэшкоду
    public PasteDTO getPasteByHashCode(String hashCod) {
        PasteEntity paste = pasteRepository.findByHashCodeAndDeadTimeAfter(hashCod, LocalDateTime.now());
        return (PasteDTO) mapper.toDTO(paste, null);
    }

    public PasteDTO getPaste(String hashCod, UserDTO user) {
        return (PasteDTO) mapper.toDTO(pasteRepository.findByHashCode(hashCod), user);
    }

    //Добавить комментарий к пасте
    public CommentDTO saveComment(long pasteId, String commentText, UserDTO user) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPasteId(pasteId);
        commentDTO.setText(commentText);
        commentDTO.setUserName(user.getUserName());
        CommentEntity commentToSave = mapper.toEntity(commentDTO, CommentEntity.class, user);
        commentToSave = commentRepository.save(commentToSave);
        commentDTO.setId(commentToSave.getId());
        return commentDTO;
    }

    //Получить все пасты конкретного пользователя
    public List<PasteDTO> getByUserName(String userName) {

        List<PasteDTO> pasteDTOS = new ArrayList<>();

        pasteRepository.findByUser(userRepository.findByUserName(userName)).forEach(el -> pasteDTOS.add((PasteDTO) mapper.toDTO(el, null)));

        return pasteDTOS;
    }

    //Удалить пасту по Хэш-коду
    public boolean deleteByHashCode(String hashCode) {

        try {
            pasteRepository.delete(pasteRepository.findByHashCode(hashCode));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Получить пасты удовлетворяющие поиску
    public List<PasteDTO> searchPastes(String searchString) {

        List<PasteDTO> dtos = new ArrayList<>();
        pasteRepository
                .findByNameIgnoreCaseContainingAndDeadTimeAfterOrDescriptionIgnoreCaseContainingAndDeadTimeAfter(
                        searchString, LocalDateTime.now(), searchString, LocalDateTime.now())
                .forEach(el -> dtos.add((PasteDTO) mapper.toDTO(el, null)));

        return dtos;
    }

    private String getHashCode() {

        byte[] array = new byte[64];
        new Random().nextBytes(array);

        String randomString = new String(array, StandardCharsets.UTF_8);
        StringBuffer r = new StringBuffer();

        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);
            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))) {
                r.append(ch);
            }
        }

        return r.toString();
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
