package com.paste_bin_clone.tests;

import com.paste_bin_clone.config.DatabaseSetupExtension;
import com.paste_bin_clone.controller.AuthenticationController;
import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.AuthenticationResponseDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(DatabaseSetupExtension.class)
class AuthenticationControllerTest extends DatabaseSetupExtension {

    @Autowired
    private AuthenticationController authenticationController;

    public static final String TEST_USER_NAME = "testUser";
    public static final String TEST_USER_PASSWORD = "testUserPassword";

    public static final UserDTO testUser = new UserDTO()
            .setUserName(TEST_USER_NAME)
            .setPassword(TEST_USER_PASSWORD);

    @Test
    @Order(1)
    void registrationPositiveTest() {
        AuthenticationResponseDTO registrationAnswer =
                assertDoesNotThrow(() -> authenticationController.registration(testUser));
        assertEquals(registrationAnswer.getUserDTO().getUserName(), testUser.getUserName());
    }

    @Test
    @Order(2)
    void registrationNegativeDuplicateUserNameTest() {
        ApplicationError error = assertThrows(
                ApplicationError.class,
                () -> authenticationController.registration(testUser),
                "нет ожидаемого исключения при попытке зарегистрироваться под существующим именем пользователя");
        assertNotNull(error.getErrors().get(ERRORS.USER_NAME_ALREADY_EXIST));
    }

    @Test
    @Order(3)
    void loginPositiveTest() {
        AuthenticationResponseDTO loginAnswerCorrect =
                assertDoesNotThrow(() -> authenticationController.login(
                        new AuthenticationRequestDTO()
                                .setUserName(testUser.getUserName())
                                .setPassword(testUser.getPassword()))
                );
    }

    @Test
    @Order(4)
    void loginWrongPasswordTest() {
        ApplicationError error =
                assertThrows(
                        ApplicationError.class, () ->
                                authenticationController.login(
                                        new AuthenticationRequestDTO()
                                                .setUserName(testUser.getUserName())
                                                .setPassword(testUser.getPassword() + "dfsf"))
                );
    }
}
