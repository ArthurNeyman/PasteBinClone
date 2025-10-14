package com.paste_bin_clone.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paste_bin_clone.config.CommonTest;
import com.paste_bin_clone.dto.AuthenticationRequestDTO;
import com.paste_bin_clone.dto.UserDTO;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(CommonTest.class)
@AutoConfigureMockMvc
class AuthenticationControllerTest extends CommonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static final String TEST_USER_NAME = "testUser";
    public static final String TEST_USER_PASSWORD = "testUserPassword";

    public static final UserDTO testUser = new UserDTO()
        .setUsername(TEST_USER_NAME)
        .setPassword(TEST_USER_PASSWORD);

    @Test
    @Order(1)
    void registrationPositiveTest() throws Exception {
        registerUser(testUser)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userDTO.username").value(TEST_USER_NAME));
    }

    @Test
    @Order(2)
    void registrationNegativeDuplicateUserNameTest() throws Exception {
        registerUser(testUser)
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.jsonPath("$.errors.USER_NAME_ALREADY_EXIST").exists());
    }

    @Test
    @Order(3)
    void loginPositiveTest() throws Exception {
        loginUser(testUser.getUsername(), testUser.getPassword())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    @Order(4)
    void loginWrongPasswordTest() throws Exception {
        loginUser(testUser.getUsername(), testUser.getPassword() + "dfsf")
            .andExpect(MockMvcResultMatchers.status().isUnauthorized())
            .andExpect(MockMvcResultMatchers.jsonPath("$.errors.WRONG_USER_NAME_OR_PASSWORD").exists());
    }

    private ResultActions registerUser(UserDTO user) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/auth/registration")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)));
    }

    private ResultActions loginUser(String username, String password) throws Exception {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO()
            .setUserName(username)
            .setPassword(password);

        return mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));
    }

    private String registerAndGetToken(UserDTO user) throws Exception {
        String response = registerUser(user)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        return objectMapper.readTree(response).get("token").asText();
    }
}
