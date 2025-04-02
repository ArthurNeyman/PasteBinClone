package com.paste_bin_clone.services.impl;

import com.paste_bin_clone.dto.*;
import com.paste_bin_clone.entities.*;
import com.paste_bin_clone.other.ACCESS_LEVEL;
import com.paste_bin_clone.other.LIFETIME;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperService {

    public <T, S> S toEntity(T dto, Class<S> toEntityClass, UserDTO user) {

        if (dto instanceof PasteDTO) {

            PasteEntity pasteEntity = new PasteEntity();
            PasteDTO pasteDTO = (PasteDTO) dto;

            pasteEntity.setId(pasteDTO.getId());
            pasteEntity.setDateCreate(pasteDTO.getDateCreate());
            pasteEntity.setDeadTime(pasteDTO.getDeadTime());
            pasteEntity.setHashCode(pasteDTO.getHashCode());
            pasteEntity.setAccess(pasteDTO.getAccess().toString());
            pasteEntity.setDescription(pasteDTO.getDescription());
            pasteEntity.setLifetime(pasteDTO.getLifetime().toString());
            pasteEntity.setName(pasteDTO.getName());

            if (pasteDTO.getUser() != null)
                pasteEntity.setUser(this.toEntity(((PasteDTO) dto).getUser(), UserEntity.class, user));
            else pasteEntity.setUser(null);

            List<CommentEntity> commentEntities = new ArrayList<>();
            if (pasteDTO.getComments() != null) {
                pasteDTO.getComments().forEach(el -> commentEntities.add(this.toEntity(el, CommentEntity.class, pasteDTO.getUser())));
                pasteEntity.setComments(commentEntities);
            } else {
                pasteEntity.setComments(null);
            }


            return toEntityClass.cast(pasteEntity);
        }

        if (dto instanceof CommentDTO) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setId(((CommentDTO) dto).getId());
            commentEntity.setPasteId(((CommentDTO) dto).getPasteId());
            commentEntity.setUserId(user.getUserId());
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

    public <T> Object toDTO(T entity, UserDTO user) {

        if (entity instanceof PasteEntity) {

            PasteDTO pasteDTO = new PasteDTO();
            PasteEntity pasteEntity = (PasteEntity) entity;

            pasteDTO.setId(pasteEntity.getId());
            pasteDTO.setDateCreate(pasteEntity.getDateCreate());
            pasteDTO.setDeadTime(pasteEntity.getDeadTime());
            pasteDTO.setHashCode(pasteEntity.getHashCode());
            pasteDTO.setAccess(ACCESS_LEVEL.valueOf(pasteEntity.getAccess()));
            pasteDTO.setDescription(pasteEntity.getDescription());
            pasteDTO.setLifetime(LIFETIME.valueOf(pasteEntity.getLifetime()));
            pasteDTO.setName(pasteEntity.getName());

            UserEntity userEntity = pasteEntity.getUser();
            pasteDTO.setUser((UserDTO) this.toDTO(userEntity, user));

            if (pasteEntity.getUser() != null)
                pasteEntity.getUser().setPassword("");

            return pasteDTO;
        }

        if (entity instanceof CommentEntity) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(((CommentEntity) entity).getId());
            commentDTO.setPasteId(((CommentEntity) entity).getPasteId());
            commentDTO.setText(((CommentEntity) entity).getText());

            if (user != null) {
                commentDTO.setUserName(user.getUserName());
            } else commentDTO.setUserName("");
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
