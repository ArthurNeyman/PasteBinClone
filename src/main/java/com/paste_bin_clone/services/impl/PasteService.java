package com.paste_bin_clone.services.impl;

import com.paste_bin_clone.dto.*;
import com.paste_bin_clone.entities.CommentEntity;
import com.paste_bin_clone.entities.LifeTimeEntity;
import com.paste_bin_clone.entities.PasteEntity;
import com.paste_bin_clone.repositories.*;
import com.paste_bin_clone.services.IMapperService;
import com.paste_bin_clone.services.IPasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PasteService implements IPasteService {

    @Autowired
    private PasteRepository pasteRepository;
    @Autowired
    private LifeTimeRepository lifeTimeRepository;
    @Autowired
    private AccessRepository accessRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Qualifier("mapperService")
    @Autowired
    private IMapperService mapper;

    private PasswordEncoder passwordEncoder;

    //------------------------------------------------------------------------------------------------------------------

    //Получить список доступных вариантов времени жизни пасты
    public List<LifeTimeDTO> getLifeTimeList() {

        List<LifeTimeDTO> lifeTimeDTOS = new ArrayList<>();
        lifeTimeRepository.findAll().forEach(el -> lifeTimeDTOS.add((LifeTimeDTO) mapper.toDTO(el)));

        return lifeTimeDTOS;
    }

    //Получить список вариантов доступа к пастам
    public List<AccessDTO> getAccessList() {

        List<AccessDTO> accessDTOS = new ArrayList<>();
        accessRepository.findAll().forEach(el -> accessDTOS.add((AccessDTO) mapper.toDTO(el)));

        return accessDTOS;
    }

    //получить 10 последних паст
    public List<PasteDTO> getLastTenPastes() {

        List<PasteDTO> list = new ArrayList<>();
        pasteRepository.findFirst10ByAccessIdAndDeadTimeAfterOrderByDateCreate(1, LocalDateTime.now())
                .forEach(el -> list.add((PasteDTO) mapper.toDTO(el)));
        return list;
    }

    //Сохранить пасту
    public PasteDTO savePaste(PasteDTO pasteDTO) {
        LocalDateTime time = LocalDateTime.now();
        pasteDTO.setDateCreate(time);
        Optional<LifeTimeEntity> lifeTimeValue = lifeTimeRepository.findById(pasteDTO.getLifetimeId());
        pasteDTO.setDeadTime(time.plusMinutes(lifeTimeValue.get().getMinutes()));
        pasteDTO.setHashCode(getHashCode());
        return (PasteDTO) mapper.toDTO(pasteRepository.save(mapper.toEntity(pasteDTO, PasteEntity.class)));
    }

    //Пoлучить пасту по Хэшкоду
    public PasteDTO getPasteByHashCode(String hashCod) {
        return (PasteDTO) mapper.toDTO(pasteRepository.findByHashCodeAndDeadTimeAfter(hashCod, LocalDateTime.now()));
    }

    public PasteDTO getPaste(String hashCod) {
        return (PasteDTO) mapper.toDTO(pasteRepository.findByHashCode(hashCod));
    }

    //Добавить комментарий к пасте
    public boolean saveComment(CommentDTO commentDTO) {
        if (pasteRepository.getOne(commentDTO.getPasteId()).getDeadTime().isAfter(LocalDateTime.now()))
            return commentRepository.save(mapper.toEntity(commentDTO, CommentEntity.class)) != null;
        else return false;
    }

    //Получить все пасты конкретного пользователя
    public List<PasteDTO> getByUserName(String userName) {

        List<PasteDTO> pasteDTOS = new ArrayList<>();

        pasteRepository.findByUser(userRepository.findByUserName(userName)).forEach(el -> pasteDTOS.add((PasteDTO) mapper.toDTO(el)));

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
                .forEach(el -> dtos.add((PasteDTO) mapper.toDTO(el)));

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
}
