package com.paste_bin_clone.services;

import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.entities.UserEntity;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.other.ROLES;
import com.paste_bin_clone.repositories.CommentRepository;
import com.paste_bin_clone.repositories.PasteRepository;
import com.paste_bin_clone.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService extends CommonService {

    private final UserRepository userRepository;
    private final PasteRepository pasteRepository;
    private final CommentRepository commentRepository;

    public static final ConcurrentHashMap<String, UserDTO> USERS = new ConcurrentHashMap<>();

    public UserDTO registration(UserDTO user) {
        UserEntity userEntity = convertTo(user, UserEntity.class);
        userEntity.setRole(ROLES.USER.toString());
        userEntity = userRepository.save(userEntity);
        UserDTO userAnswer = convertTo(userEntity, UserDTO.class);
        USERS.put(userAnswer.getUserName(), userAnswer);
        return userAnswer;
    }

    public boolean usernameExist(String userName) {
        return userRepository.userNameExist(userName);
    }

    public UserDTO findByUserName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName);
        return convertTo(userEntity, UserDTO.class);
    }

    public UserEntity findEntityByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<PasteDTO> getPastes(UserDTO user) {
        return pasteRepository
            .findAllByUserIdOrderByDateCreate(user.getUserId())
            .stream()
            .map(p -> convertTo(p, PasteDTO.class))
            .collect(Collectors.toList());
    }

    public UserDTO updateProfile(UserDTO newDataUser, UserDTO oldUser) {
        if (!oldUser.getUserName().equals(newDataUser.getUserName()))
            if (userRepository.findByUserName(newDataUser.getUserName()) != null) {
                throw new ApplicationError()
                    .add(ERRORS.USER_NAME_ALREADY_EXIST, oldUser.getUserName())
                    .withStatus(HttpStatus.CONFLICT);
            }
        oldUser.setUserName(newDataUser.getUserName());
        oldUser.setEmail(newDataUser.getEmail());
        oldUser.setFirstName(newDataUser.getFirstName());
        oldUser.setLastName(newDataUser.getLastName());
        UserEntity savedEntity = userRepository.save(convertTo(oldUser, UserEntity.class));
        return convertTo(savedEntity, UserDTO.class);
    }

    public UserDTO getUser(String userName) {
        return USERS.computeIfAbsent(userName, val -> convertTo(userRepository.findByUserName(userName), UserDTO.class));
    }

    public void deleteComment(Long commentId, UserDTO userDTO) {
        commentRepository.deleteByIdAndUserId(commentId, userDTO.getUserId());
    }

    public List<CommentDTO> getUserComments(UserDTO userDTO) {
        return commentRepository.findAllByUserId(userDTO.getUserId())
            .stream()
            .map(c -> convertTo(c, CommentDTO.class))
            .collect(Collectors.toList());
    }

}
