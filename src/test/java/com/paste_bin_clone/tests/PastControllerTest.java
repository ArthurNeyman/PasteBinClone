package com.paste_bin_clone.tests;

import com.paste_bin_clone.config.DatabaseSetupExtension;
import com.paste_bin_clone.config.PostgresTestContainer;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.other.ACCESS_LEVEL;
import com.paste_bin_clone.other.ApplicationError;
import com.paste_bin_clone.other.ERRORS;
import com.paste_bin_clone.other.LIFETIME;
import com.paste_bin_clone.services.IPasteService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(DatabaseSetupExtension.class)
public class PastControllerTest extends DatabaseSetupExtension {

    @Autowired
    private IPasteService pasteService;

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

        ApplicationError error = assertThrows(
                ApplicationError.class,
                () -> pasteService.savePaste(testPaste), "нет ожидамого исключения при сохранеии пасты без требуемых полей");
        assertEquals(4, error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD).size());

        assertEquals("name", error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD).get(0));
        assertEquals("description", error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD).get(1));

        testPaste.setName(TEST_PASTE_NAME);
        testPaste.setDescription(TEST_PASTE_DESCRIPTION);

        error = assertThrows(
                ApplicationError.class,
                () -> pasteService.savePaste(testPaste), "нет ожидамого исключения при сохранеии пасты без требуемых полей");
        assertEquals(2, error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD).size());
        assertEquals("lifetime", error.getErrors().get(ERRORS.EMPTY_REQUIRED_FIELD).get(0));

        testPaste.setLifetime(LIFETIME.TEN_MINUTES);
        testPaste.setAccess(ACCESS_LEVEL.PUBLIC);

        PasteDTO pasteDTO = assertDoesNotThrow(
                () -> pasteService.savePaste(testPaste),
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
        final List<PasteDTO> pasteDTOS = new ArrayList<>();
        assertDoesNotThrow(() -> {
            pasteDTOS.addAll(pasteService.getLastTenPastes());
        });
        assertEquals(1, pasteDTOS.size());
        PasteDTO paste = pasteDTOS.get(0);
        assertEquals(TEST_PASTE_NAME, paste.getName());
        assertEquals(TEST_PASTE_DESCRIPTION, paste.getDescription());
        assertEquals(ACCESS_LEVEL.PUBLIC, paste.getAccess());
        assertEquals(LIFETIME.TEN_MINUTES, paste.getLifetime());
        assertEquals(hash_code.toString(), paste.getHashCode());
    }

    @Test
    @Order(3)
    void getByHashCode() {
    }


    @Test
    @Order(4)
    void addComment() {

    }

    @Test
    @Order(5)
    void searchPaste() {

    }
}
