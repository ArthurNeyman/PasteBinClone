package com.good_three.services.impl;

import com.good_three.dto.*;
import com.good_three.entities.*;
import com.good_three.repositories.AccessRepository;
import com.good_three.repositories.LifeTimeRepository;
import com.good_three.repositories.PasteRepository;
import com.good_three.repositories.UserRepository;
import com.good_three.services.IMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperService implements IMapperService {

    @Autowired
    private PasteRepository pasteRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private LifeTimeRepository lifeTimeRepository;

    @Autowired
    private UserRepository userRepository;

    public <T> Object toEntity(T dto) {

        if (dto instanceof PasteDTO) {

            PasteEntity pasteEntity = new PasteEntity();

            pasteEntity.setId(((PasteDTO) dto).getId());
            pasteEntity.setDateCreate(((PasteDTO) dto).getDateCreate());
            pasteEntity.setDeadTime(((PasteDTO) dto).getDeadTime());
            pasteEntity.setHashCode(((PasteDTO) dto).getHashCode());
            pasteEntity.setAccess(accessRepository.findById(((PasteDTO) dto).getAccessId()).get());
            pasteEntity.setDescription(((PasteDTO) dto).getDescription());
            pasteEntity.setLifetime(lifeTimeRepository.findById(((PasteDTO) dto).getLifetimeId()).get());
            pasteEntity.setName(((PasteDTO) dto).getName());

            List<CommentEntity> commentEntities = new ArrayList<>();
            if (((PasteDTO) dto).getComments() != null) {
                ((PasteDTO) dto).getComments().forEach(el -> commentEntities.add((CommentEntity) this.toEntity(el)));
                pasteEntity.setComments(commentEntities);
            } else {
                pasteEntity.setComments(null);
            }
            if(((PasteDTO) dto).getUser()!=null)
                pasteEntity.setUser(userRepository.findByUsername(((PasteDTO) dto).getUser().getUsername()));
            else pasteEntity.setUser(null);
            return pasteEntity;
        }

        if (dto instanceof AccessDTO) {

            AccessEntity accessEntity = new AccessEntity();

            accessEntity.setId(((AccessDTO) dto).getId());
            accessEntity.setName(((AccessDTO) dto).getName());

            return accessEntity;
        }

        if (dto instanceof LifeTimeDTO) {
            LifeTimeEntity lifetimeEntity = new LifeTimeEntity();

            lifetimeEntity.setId(((LifeTimeDTO) dto).getId());
            lifetimeEntity.setMinutes(((LifeTimeDTO) dto).getMinutes());
            lifetimeEntity.setName(((LifeTimeDTO) dto).getName());

            return lifetimeEntity;
        }

        if (dto instanceof CommentDTO) {

            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setId(((CommentDTO) dto).getId());
            commentEntity.setPaste(pasteRepository.getOne(((CommentDTO) dto).getPasteId()));
            commentEntity.setUser(userRepository.findByUsername(((CommentDTO) dto).getUserName()));
            commentEntity.setText(((CommentDTO) dto).getText());

            return commentEntity;
        }

        if (dto instanceof UserDTO) {

            UserEntity userEntity=new UserEntity();

            userEntity.setUsername(((UserDTO) dto).getUsername());
            userEntity.setFirstName(((UserDTO) dto).getFirstName());
            userEntity.setLastName(((UserDTO) dto).getLastName());
            userEntity.setEmail(((UserDTO) dto).getEmail());
            userEntity.setPassword(((UserDTO) dto).getPassword());
            userEntity.setRoles(((UserDTO) dto).getRoles());

            return userEntity;
        }

        return null;
    }

    public <T> Object toDTO(T entity) {

        if (entity instanceof PasteEntity) {

            PasteDTO pasteDTO = new PasteDTO();


            pasteDTO.setId(((PasteEntity) entity).getId());
            pasteDTO.setDateCreate(((PasteEntity) entity).getDateCreate());
            pasteDTO.setDeadTime(((PasteEntity) entity).getDeadTime());
            pasteDTO.setHashCode(((PasteEntity) entity).getHashCode());
            pasteDTO.setAccessId(((PasteEntity) entity).getAccess().getId());
            pasteDTO.setDescription(((PasteEntity) entity).getDescription());
            pasteDTO.setLifetimeId(((PasteEntity) entity).getLifetime().getId());
            pasteDTO.setName(((PasteEntity) entity).getName());

            if (((PasteEntity) entity).getComments() != null) {
                List<CommentDTO> comments = new ArrayList<>();
                ((PasteEntity) entity).getComments().forEach(el -> comments.add((CommentDTO) this.toDTO(el)));
                pasteDTO.setComments(comments);
            } else
                pasteDTO.setComments(null);

            UserEntity user = ((PasteEntity) entity).getUser();
            pasteDTO.setUser((UserDTO) this.toDTO(user));

            if(((PasteEntity) entity).getUser()!=null)
                ((PasteEntity) entity).getUser().setPassword("");

            return pasteDTO;
        }

        if (entity instanceof AccessEntity) {

            AccessDTO accessDTO = new AccessDTO();

            accessDTO.setId(((AccessEntity) entity).getId());
            accessDTO.setName(((AccessEntity) entity).getName());

            return accessDTO;
        }

        if (entity instanceof LifeTimeEntity) {
            LifeTimeDTO lifeTimeDTO = new LifeTimeDTO();

            lifeTimeDTO.setId(((LifeTimeEntity) entity).getId());
            lifeTimeDTO.setMinutes(((LifeTimeEntity) entity).getMinutes());
            lifeTimeDTO.setName(((LifeTimeEntity) entity).getName());

            return lifeTimeDTO;
        }

        if (entity instanceof CommentEntity) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(((CommentEntity) entity).getId());
            commentDTO.setPasteId(((CommentEntity) entity).getPaste().getId());
            commentDTO.setText(((CommentEntity) entity).getText());

            if (((CommentEntity) entity).getUser() != null)
                commentDTO.setUserName(((CommentEntity) entity).getUser().getUsername());
            else commentDTO.setUserName("");
            return commentDTO;
        }

        if (entity instanceof UserEntity) {

            UserDTO userDTO=new UserDTO();

            userDTO.setUsername(((UserEntity) entity).getUsername());
            userDTO.setFirstName(((UserEntity) entity).getFirstName());
            userDTO.setLastName(((UserEntity) entity).getLastName());
            userDTO.setEmail(((UserEntity) entity).getEmail());
//            userDTO.setPassword(((UserEntity) entity).getPassword());
            userDTO.setRoles(((UserEntity) entity).getRoles());

            return userDTO;
        }

        return null;

    }
}
