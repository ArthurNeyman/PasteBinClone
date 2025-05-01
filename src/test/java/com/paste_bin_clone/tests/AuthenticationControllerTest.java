package com.paste_bin_clone.tests;

import com.paste_bin_clone.config.DatabaseSetupExtension;
import com.paste_bin_clone.controller.AuthenticationController;
import com.paste_bin_clone.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(DatabaseSetupExtension.class)
class AuthenticationControllerTest extends DatabaseSetupExtension {

    @Autowired
    private AuthenticationController authenticationController;

    public static final String TEST_USER_NAME = "testUser";
    public static final String TEST_USER_PASSWORD = "testUserPassword";

    public static final UserDTO testUser = new UserDTO().setUserName(TEST_USER_NAME).setPassword(TEST_USER_PASSWORD);

    @Test
    void registrationTest() {

//      AuthenticationRequestAnswerDTO registrationAnswer =
//                authenticationController.registration(testUser);
//
//        ApplicationError error = assertThrows(
//                ApplicationError.class,
//                () -> authenticationController.registration(testUser),
//                "нет ожидаемого исключения при попытке зарегистрироваться под существующим именем пользователя");
//        assertNotNull(error.getErrors().get(ERRORS.USER_NAME_ALREADY_EXIST));
//
//        loginTest();
    }

    void loginTest() {
//        ResponseStatusDTO<AuthenticationRequestAnswerDTO> loginAnswerCorrect =
//                authenticationController.login(
//                        new AuthenticationRequestDTO().setUserName(testUser.getUserName()).setPassword(testUser.getPassword()));
//        assertEquals(HttpStatus.OK, loginAnswerCorrect.getStatus());
//        ResponseStatusDTO<AuthenticationRequestAnswerDTO> loginAnswerWrong =
//                authenticationController.login(
//                        new AuthenticationRequestDTO().setUserName(testUser.getUserName()).setPassword(testUser.getPassword() + "dfsf"));
//        assertEquals(HttpStatus.FORBIDDEN, loginAnswerWrong.getStatus());
    }
}
