package com.good_three.services.impl;

import com.good_three.dto.*;
import com.good_three.entities.CommentEntity;
import com.good_three.entities.PasteEntity;
import com.good_three.repositories.*;
import com.good_three.services.IMapperService;
import com.good_three.services.IPasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
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
        pasteRepository.findFirst10ByAccessIdAndDeadTimeAfterOrderByDateCreate(1, new Date())
                .forEach(el -> list.add((PasteDTO) mapper.toDTO(el)));
        return list;
    }

    //Сохранить пасту
    public PasteDTO savePaste(PasteDTO pasteDTO) {

        Calendar calendar = Calendar.getInstance();
        pasteDTO.setDateCreate(calendar.getTime());
        calendar.add(Calendar.MINUTE, (int) lifeTimeRepository.findById(pasteDTO.getLifetimeId()).get().getMinutes());
        pasteDTO.setDeadTime(calendar.getTime());

        pasteDTO.setHashCode(getHashCode());

        return (PasteDTO) mapper.toDTO((PasteEntity) pasteRepository.save((PasteEntity) mapper.toEntity(pasteDTO)));
    }

    //Пoлучить пасту по Хэшкоду
    public PasteDTO getPasteByHashCode(String hashCod) {
        return (PasteDTO) mapper.toDTO(pasteRepository.findByHashCodeAndDeadTimeAfter(hashCod, new Date()));
    }

    public PasteDTO getPaste(String hashCod) {
        return (PasteDTO) mapper.toDTO(pasteRepository.findByHashCode(hashCod));
    }

    //Добавить комментарий к пасте
    public boolean saveComment(CommentDTO commentDTO) {
        if(pasteRepository.getOne(commentDTO.getPasteId()).getDeadTime().after(new Date()))
            return commentRepository.save((CommentEntity) mapper.toEntity(commentDTO)) != null;
        else return false;
    }

    //Получить все пасты конкретного пользователя
    public List<PasteDTO> getByUserName(String userName) {

        List<PasteDTO> pasteDTOS = new ArrayList<>();

        pasteRepository.findByUser(userRepository.findByUsername(userName)).forEach(el -> pasteDTOS.add((PasteDTO) mapper.toDTO(el)));

        return pasteDTOS;
    }

    //Удалить пасту по Хэш-коду
    public boolean deleteByHashCode(String hashCode) {

         try{
             pasteRepository.delete(pasteRepository.findByHashCode(hashCode));
             return  true;
         }catch (Exception e){
             return false;
         }
    }

    //Получить пасты удовлетворяющие поиску
    public List<PasteDTO> searchPastes(String searchString) {

        List<PasteDTO> dtos = new ArrayList<>();
        pasteRepository
                .findByNameIgnoreCaseContainingAndDeadTimeAfterOrDescriptionIgnoreCaseContainingAndDeadTimeAfter(
                        searchString, new Date(), searchString, new Date())
                .forEach(el -> dtos.add((PasteDTO) mapper.toDTO(el)));

        return dtos;
    }

    private String getHashCode(){

        byte[] array = new byte[64];
        new Random().nextBytes(array);

        String randomString = new String(array, Charset.forName("UTF-8"));
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
