package com.paste_bin_clone.other;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum AccessLevel {
    PUBLIC("Публичный",
        "Видна всем пользователям в списке последних паст и в вашем профиле"),
    UNLISTED("По ссылке",
        "Доступна для просмотра другими пользователями только по ссылке"),
    PRIVATE("Приватный",
        "Эта паста видна только вам");
    private final String description;
    private final String name;

    AccessLevel(String name, String description) {
        this.description = description;
        this.name = name;
    }


    @Getter
    public static final Map<AccessLevel, Map<String, String>> accessLevelList =
            Arrays.stream(values())
                    .collect(Collectors.toMap(el -> el, el ->
                            Map.of(
                                    "name", el.name,
                                    "description", el.description)));

}
