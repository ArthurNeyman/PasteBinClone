package com.paste_bin_clone.other;

public enum ROLES {

    USER("Пользователь"),
    ADMIN("Администратор");
    private final String name;
    ROLES(String name) {
        this.name = name;
    }
}
