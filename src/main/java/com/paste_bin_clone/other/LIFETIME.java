package com.paste_bin_clone.other;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum LIFETIME {

    TEN_MINUTES(10, "10 минут"),

    ONE_HOUR(60, "1 час"),

    THREE_HOURS(180, "3 часа"),

    ONE_DAY(1440, "1 день"),

    ONE_WEEK(10080, "1 неделя"),

    ONE_MONTH(43200, "1 месяц"),

    NO_LIMIT(null, "Без ограничения по времени");

    private final Integer minutes;
    private final String description;

    LIFETIME(Integer minutes, String description) {
        this.minutes = minutes;
        this.description = description;
    }

    private static final Map<LIFETIME, String> lifeTimesList =
            Arrays.stream(values())
                    .collect(Collectors.toMap(el->el, e -> e.description));

    public static Map<LIFETIME, String> getLifTimes() {
        return lifeTimesList;
    }

}
