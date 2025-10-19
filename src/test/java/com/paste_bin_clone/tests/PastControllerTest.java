package com.paste_bin_clone.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paste_bin_clone.config.CommonTest;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.other.AccessLevel;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.other.LifeTime;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(CommonTest.class)
@AutoConfigureMockMvc
public class PastControllerTest extends CommonTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String TEST_PASTE_NAME = "testPaste";
    private static final String SEARCH_STRING = "HelloWorld";
    private static final String TEST_PASTE_DESCRIPTION = "\"public class " + SEARCH_STRING + "} {\\n\" +\n" +
        "                \"public static void main(String[] args) {\\n\" +\n" +
        "                \"System.out.println(\\\" Hello, World!\\\");\\n\" +\n" +
        "                \"}\\n\" +\n" +
        "                \"}\\n\" +\n";

    public static final StringBuilder hash_code = new StringBuilder();

    @Test
    @Order(1)
    void saveTest() throws Exception {

        PasteDTO testPaste = new PasteDTO();

        Map<String, AtomicBoolean> emptyRequiredFields = new HashMap<>(Map.of(
            "name", new AtomicBoolean(false),
            "description", new AtomicBoolean(false),
            "lifetime", new AtomicBoolean(false),
            "access", new AtomicBoolean(false)));

        ResultActions result = createPaste(testPaste)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

        ApplicationError error = objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(), ApplicationError.class);

        assertEquals(emptyRequiredFields.size(), error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD).size());

        for (String field : error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD)) {
            emptyRequiredFields.get(field).set(true);
        }
        assertTrue(emptyRequiredFields.values().stream().allMatch(val -> true));

        testPaste.setName(TEST_PASTE_NAME);
        emptyRequiredFields.remove("name");

        testPaste.setDescription(TEST_PASTE_DESCRIPTION);
        emptyRequiredFields.remove("description");

        emptyRequiredFields.get("lifetime").set(false);
        emptyRequiredFields.get("access").set(false);

        result = createPaste(testPaste)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

        error = objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(), ApplicationError.class);

        for (String field : error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD)) {
            emptyRequiredFields.get(field).set(true);
        }

        assertTrue(emptyRequiredFields.values().stream().allMatch(val -> true));

        testPaste.setLifeTime(LifeTime.TEN_MINUTES);
        testPaste.setAccess(AccessLevel.PUBLIC);

        result = createPaste(testPaste)
            .andExpect(MockMvcResultMatchers.status().isOk());

        PasteDTO pasteDTO = objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(), PasteDTO.class);

        assertEquals(TEST_PASTE_NAME, pasteDTO.getName());
        assertEquals(TEST_PASTE_DESCRIPTION, pasteDTO.getDescription());
        assertEquals(AccessLevel.PUBLIC, pasteDTO.getAccess());
        assertEquals(LifeTime.TEN_MINUTES, pasteDTO.getLifeTime());

        hash_code.append(pasteDTO.getHashCode());
    }

    @Test
    @Order(2)
    void getTest() throws Exception {
        ResultActions result =
            mockMvc.perform(MockMvcRequestBuilders.get("/paste")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""));

        result.andExpect(MockMvcResultMatchers.status().isOk());

        List<PasteDTO> pasteDTOS =
            List.of(objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(), PasteDTO[].class));

        assertEquals(1, pasteDTOS.size());
        assertTrue(checkPaste(pasteDTOS.get(0)),
            "Поля созданной пасты не совпадают с полученными данными"
        );
    }

    @Test
    @Order(3)
    void getByHashCodeTest() throws Exception {

        ResultActions result =
            mockMvc.perform(MockMvcRequestBuilders.get("/paste/" + hash_code)
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());

        PasteDTO pasteDTO = objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(), PasteDTO.class);

        assertTrue(checkPaste(pasteDTO),
            "Поля созданной пасты не совпадают с полученными данными"
        );

        result =
            mockMvc.perform(MockMvcRequestBuilders.get("/paste/" + hash_code + "aaa")
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @Order(5)
    void searchPasteTest() throws Exception {
        ResultActions result =
            mockMvc.perform(MockMvcRequestBuilders.get("/paste/search/" + SEARCH_STRING)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""));

        List<PasteDTO> pasteDTO = List.of(objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(), PasteDTO[].class));
        ;
        assertEquals(1, pasteDTO.size());
        assertEquals(pasteDTO.get(0).getHashCode(), hash_code.toString());
    }

    boolean checkPaste(PasteDTO paste) {
        return
            TEST_PASTE_NAME.equals(paste.getName()) &&
                TEST_PASTE_DESCRIPTION.equals(paste.getDescription()) &&
                AccessLevel.PUBLIC.equals(paste.getAccess()) &&
                LifeTime.TEN_MINUTES.equals(paste.getLifeTime()) &&
                hash_code.toString().equals(paste.getHashCode());
    }

    private ResultActions createPaste(PasteDTO pasteDTO) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/paste")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pasteDTO)));
    }

}
