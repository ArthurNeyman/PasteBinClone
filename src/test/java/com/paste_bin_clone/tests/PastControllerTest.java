package com.paste_bin_clone.tests;

import com.paste_bin_clone.config.DatabaseSetupExtension;
import com.paste_bin_clone.controller.PasteController;
import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.other.AccessLevel;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.other.LIFETIME;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(DatabaseSetupExtension.class)
public class PastControllerTest extends DatabaseSetupExtension {

    @Autowired
    private PasteController pasteController;

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
    void save() {

        PasteDTO testPaste = new PasteDTO();

        Map<String, AtomicBoolean> emptyRequiredFields = new HashMap<>(Map.of(
                "name", new AtomicBoolean(false),
                "description", new AtomicBoolean(false),
                "lifetime", new AtomicBoolean(false),
                "access", new AtomicBoolean(false)));

        ApplicationError error = assertThrows(
                ApplicationError.class,
                () -> pasteController.save(testPaste),
                "нет ожидамого исключения при сохранеии пасты без требуемых полей"
        );

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

        error = assertThrows(
                ApplicationError.class,
                () -> pasteController.save(testPaste),
                "нет ожидамого исключения при сохранеии пасты без требуемых полей"
        );

        for (String field : error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD)) {
            emptyRequiredFields.get(field).set(true);
        }

        assertTrue(emptyRequiredFields.values().stream().allMatch(val -> true));

        testPaste.setLifetime(LIFETIME.TEN_MINUTES);
        testPaste.setAccess(AccessLevel.PUBLIC);

        PasteDTO pasteDTO = assertDoesNotThrow(
                () -> pasteController.save(testPaste),
                "Ошибка при сохрранении пасты от не автоизиррованного пользователя при всех заполненных требуемых полях"
        );

        assertEquals(TEST_PASTE_NAME, pasteDTO.getName());
        assertEquals(TEST_PASTE_DESCRIPTION, pasteDTO.getDescription());
        assertEquals(AccessLevel.PUBLIC, pasteDTO.getAccess());
        assertEquals(LIFETIME.TEN_MINUTES, pasteDTO.getLifetime());
        hash_code.append(pasteDTO.getHashCode());
    }

    @Test
    @Order(2)
    void get() {
        List<PasteDTO> pasteDTOS =
                assertDoesNotThrow(
                        () -> pasteController.get()
                );
        assertEquals(1, pasteDTOS.size());
        assertTrue(checkPaste(pasteDTOS.get(0)),
                "Поля созданной пасты не совпадают с полученными данными"
        );
    }

    @Test
    @Order(3)
    void getByHashCode() {

        PasteDTO pasteDTO =
                assertDoesNotThrow(() ->
                        pasteController.getByHashCode(hash_code.toString())
                );

        assertTrue(checkPaste(pasteDTO),
                "Поля созданной пасты не совпадают с полученными данными"
        );

        assertNull(pasteController.getByHashCode(hash_code + "aa"));

    }

    @Test
    @Order(4)
    void addComment() {
        String commentText = "testComment";
        PasteDTO pasteDTO =
                assertDoesNotThrow(() ->
                        pasteController.getByHashCode(hash_code.toString())
                );
        PasteDTO finalPasteDTO = pasteDTO;
        CommentDTO commentDTO = assertDoesNotThrow(
                () -> pasteController.addComment(finalPasteDTO.getId(), commentText)
        );
        assertEquals(commentDTO.getText(), commentText);

        pasteDTO =
                assertDoesNotThrow(() ->
                        pasteController.getByHashCode(hash_code.toString())
                );

        assertEquals(pasteDTO.getComments().get(0).getText(), commentText);
    }

    @Test
    @Order(5)
    void searchPaste() {
        List<PasteDTO> pasteDTO = assertDoesNotThrow(
                () -> pasteController.searchPaste(SEARCH_STRING)
        );
        assertEquals(1, pasteDTO.size());
        assertEquals(pasteDTO.get(0).getHashCode(), hash_code.toString());
    }

    boolean checkPaste(PasteDTO paste) {
        return
                TEST_PASTE_NAME.equals(paste.getName()) &&
                        TEST_PASTE_DESCRIPTION.equals(paste.getDescription()) &&
                        AccessLevel.PUBLIC.equals(paste.getAccess()) &&
                        LIFETIME.TEN_MINUTES.equals(paste.getLifetime()) &&
                        hash_code.toString().equals(paste.getHashCode());
    }
}
