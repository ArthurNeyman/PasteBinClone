package com.paste_bin_clone.services.impl;

import com.paste_bin_clone.dto.*;
import com.paste_bin_clone.entities.*;
import com.paste_bin_clone.repositories.AccessRepository;
import com.paste_bin_clone.repositories.LifeTimeRepository;
import com.paste_bin_clone.repositories.PasteRepository;
import com.paste_bin_clone.repositories.UserRepository;
import com.paste_bin_clone.services.IMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public <T, S> S toEntity(T dto, Class<S> toEntityClass) {

        if (dto instanceof PasteDTO) {

            PasteEntity pasteEntity = new PasteEntity();
            PasteDTO pasteDTO = (PasteDTO) dto;

            pasteEntity.setId(pasteDTO.getId());
            pasteEntity.setDateCreate(pasteDTO.getDateCreate());
            pasteEntity.setDeadTime(pasteDTO.getDeadTime());
            pasteEntity.setHashCode(pasteDTO.getHashCode());
            pasteEntity.setAccess(accessRepository.findById(pasteDTO.getAccessId()).get());
            pasteEntity.setDescription(pasteDTO.getDescription());
            pasteEntity.setLifetime(lifeTimeRepository.findById(pasteDTO.getLifetimeId()).get());
            pasteEntity.setName(pasteDTO.getName());

            List<CommentEntity> commentEntities = new ArrayList<>();
            if (pasteDTO.getComments() != null) {
                pasteDTO.getComments().forEach(el -> commentEntities.add(this.toEntity(el, CommentEntity.class)));
                pasteEntity.setComments(commentEntities);
            } else {
                pasteEntity.setComments(null);
            }
            if (pasteDTO.getUser() != null)
                pasteEntity.setUser(userRepository.findByUserName(pasteDTO.getUser().getUserName()));
            else pasteEntity.setUser(null);

            return toEntityClass.cast(pasteEntity);
        }

        if (dto instanceof AccessDTO) {
            AccessEntity accessEntity = new AccessEntity();
            accessEntity.setId(((AccessDTO) dto).getId());
            accessEntity.setName(((AccessDTO) dto).getName());
            return toEntityClass.cast(accessEntity);

        }

        if (dto instanceof LifeTimeDTO) {
            LifeTimeEntity lifetimeEntity = new LifeTimeEntity();
            lifetimeEntity.setId(((LifeTimeDTO) dto).getId());
            lifetimeEntity.setMinutes(((LifeTimeDTO) dto).getMinutes());
            lifetimeEntity.setName(((LifeTimeDTO) dto).getName());
            return toEntityClass.cast(lifetimeEntity);
        }

        if (dto instanceof CommentDTO) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setId(((CommentDTO) dto).getId());
            commentEntity.setPaste(pasteRepository.getOne(((CommentDTO) dto).getPasteId()));
            commentEntity.setUser(userRepository.findByUserName(((CommentDTO) dto).getUserName()));
            commentEntity.setText(((CommentDTO) dto).getText());
            return toEntityClass.cast(commentEntity);

        }

        if (dto instanceof UserDTO) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(((UserDTO) dto).getUserName());
            userEntity.setFirstName(((UserDTO) dto).getFirstName());
            userEntity.setLastName(((UserDTO) dto).getLastName());
            userEntity.setEmail(((UserDTO) dto).getEmail());
            userEntity.setPassword(((UserDTO) dto).getPassword());
            userEntity.setRoles(((UserDTO) dto).getRoles());
            return toEntityClass.cast(userEntity);
        }
        return null;
    }

    public <T> Object toDTO(T entity) {

        if (entity instanceof PasteEntity) {

            PasteDTO pasteDTO = new PasteDTO();
            PasteEntity pasteEntity = (PasteEntity) entity;

            pasteDTO.setId(pasteEntity.getId());
            pasteDTO.setDateCreate(pasteEntity.getDateCreate());
            pasteDTO.setDeadTime(pasteEntity.getDeadTime());
            pasteDTO.setHashCode(pasteEntity.getHashCode());
            pasteDTO.setAccessId(pasteEntity.getAccess().getId());
            pasteDTO.setDescription(pasteEntity.getDescription());
            pasteDTO.setLifetimeId(pasteEntity.getLifetime().getId());
            pasteDTO.setName(pasteEntity.getName());

            pasteDTO.setComments(pasteEntity
                    .getComments()
                    .stream()
                    .map(commentEntity -> (CommentDTO) this.toDTO(commentEntity))
                    .collect(Collectors.toList()));

            UserEntity user = pasteEntity.getUser();
            pasteDTO.setUser((UserDTO) this.toDTO(user));

            if (pasteEntity.getUser() != null)
                pasteEntity.getUser().setPassword("");

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
                commentDTO.setUserName(((CommentEntity) entity).getUser().getUserName());
            else commentDTO.setUserName("");
            return commentDTO;
        }

        if (entity instanceof UserEntity) {

            UserDTO userDTO = new UserDTO();

            userDTO.setUserName(((UserEntity) entity).getUserName());
            userDTO.setFirstName(((UserEntity) entity).getFirstName());
            userDTO.setLastName(((UserEntity) entity).getLastName());
            userDTO.setEmail(((UserEntity) entity).getEmail());
            userDTO.setPassword(((UserEntity) entity).getPassword());
            userDTO.setRoles(((UserEntity) entity).getRoles());

            return userDTO;
        }

        return null;

    }
}
