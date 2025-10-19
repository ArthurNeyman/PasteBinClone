package com.paste_bin_clone.other;

import java.util.Arrays;
import java.util.List;

public enum AccessLevel {

    PUBLIC("Публичный",
        "Видна всем пользователям в списке последних паст и в вашем профиле"),
    UNLISTED("По ссылке",
        "Доступна для просмотра другими пользователями только по ссылке"),
    PRIVATE("Приватный",
        "Эта паста видна только вам");

    AccessLevel(String name, String description) {
    }

    public static List<AccessLevel> getAll() {
        return Arrays.asList(values());
    }
}
