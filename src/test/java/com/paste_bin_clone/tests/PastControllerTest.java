package com.paste_bin_clone.tests;

import com.paste_bin_clone.config.DatabaseSetupExtension;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.other.ACCESS_LEVEL;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.other.LIFETIME;
import com.paste_bin_clone.services.PasteService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(DatabaseSetupExtension.class)
public class PastControllerTest extends DatabaseSetupExtension {

    @Autowired
    private PasteService pasteService;

    public static final String TEST_PASTE_NAME = "testPaste";
    public static final String TEST_PASTE_DESCRIPTION = "\"public class HelloWorld {\\n\" +\n" +
            "                \"public static void main(String[] args) {\\n\" +\n" +
            "                \"System.out.println(\\\" Hello, World!\\\");\\n\" +\n" +
            "                \"}\\n\" +\n" +
            "                \"}\\n\" +\n";

    public static final StringBuilder hash_code = new StringBuilder();

    @Test
    @Order(1)
    void save() {

        PasteDTO testPaste = new PasteDTO();

        Map<String, Boolean> emptyRequiredFields = new HashMap<>(Map.of(
                "name", false,
                "description", false,
                "lifetime", false,
                "access", false));

        ApplicationError error = assertThrows(
                ApplicationError.class,
                () -> pasteService.savePaste(testPaste, null), "нет ожидамого исключения при сохранеии пасты без требуемых полей");

        assertEquals(emptyRequiredFields.size(), error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD).size());

        for (String field : error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD)) {
            Boolean value = emptyRequiredFields.get(field);
            value = true;
        }
        assertTrue(emptyRequiredFields.values().stream().allMatch(val -> true));

        testPaste.setName(TEST_PASTE_NAME);
        emptyRequiredFields.remove("name");

        testPaste.setDescription(TEST_PASTE_DESCRIPTION);
        emptyRequiredFields.remove("description");

        Boolean value = emptyRequiredFields.get("lifetime");
        value = false;
        value = emptyRequiredFields.get("access");
        value = false;

        error = assertThrows(
                ApplicationError.class,
                () -> pasteService.savePaste(testPaste, null), "нет ожидамого исключения при сохранеии пасты без требуемых полей");

        for (String field : error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD)) {
            value = emptyRequiredFields.get(field);
            value = true;
        }
        assertTrue(emptyRequiredFields.values().stream().allMatch(val -> true));

        testPaste.setLifetime(LIFETIME.TEN_MINUTES);
        testPaste.setAccess(ACCESS_LEVEL.PUBLIC);

        PasteDTO pasteDTO = assertDoesNotThrow(
                () -> pasteService.savePaste(testPaste, null),
                "Ошибка при сохрранении пасты от не автоизиррованного пользователя при всех заполненных требуемых полях"
        );

        assertEquals(TEST_PASTE_NAME, pasteDTO.getName());
        assertEquals(TEST_PASTE_DESCRIPTION, pasteDTO.getDescription());
        assertEquals(ACCESS_LEVEL.PUBLIC, pasteDTO.getAccess());
        assertEquals(LIFETIME.TEN_MINUTES, pasteDTO.getLifetime());
        hash_code.append(pasteDTO.getHashCode());
    }

    @Test
    @Order(2)
    void get() {
        List<PasteDTO> pasteDTOS = new ArrayList<>();
        assertDoesNotThrow(() -> {
            pasteDTOS.addAll(pasteService.getLastTenPastes(null));
        });
        assertEquals(1, pasteDTOS.size());
        PasteDTO paste = pasteDTOS.get(0);
        checkPaste(paste);
    }

    @Test
    @Order(3)
    void getByHashCode() {

        List<PasteDTO> pasteDTOS = new ArrayList<>();
        assertDoesNotThrow(() -> {
            pasteDTOS.add(pasteService.getPasteByHashCode(hash_code.toString()));
        });
        assertEquals(1, pasteDTOS.size());
        checkPaste(pasteDTOS.get(0));
        pasteDTOS.clear();

        assertDoesNotThrow(() -> {
            pasteDTOS.add(pasteService.getPasteByHashCode(hash_code + "aa"));
        });
    }


    @Test
    @Order(4)
    void addComment() {

    }

    @Test
    @Order(5)
    void searchPaste() {
    }

    void checkPaste(PasteDTO paste) {
        assertEquals(TEST_PASTE_NAME, paste.getName());
        assertEquals(TEST_PASTE_DESCRIPTION, paste.getDescription());
        assertEquals(ACCESS_LEVEL.PUBLIC, paste.getAccess());
        assertEquals(LIFETIME.TEN_MINUTES, paste.getLifetime());
        assertEquals(hash_code.toString(), paste.getHashCode());
    }
}
