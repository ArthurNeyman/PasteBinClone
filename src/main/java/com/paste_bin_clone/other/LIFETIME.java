package com.paste_bin_clone.other;

import lombok.Getter;

import java.util.Map;

@Getter
public enum LIFETIME {

    TEN_MINUTES(10),

    ONE_HOUR(60),

    THREE_HOURS(180),

    ONE_DAY(1440),

    ONE_WEEK(10080),

    ONE_MONTH(43200),

    NO_LIMIT(null);

    private final Integer minutes;

    LIFETIME(Integer minutes) {
        this.minutes = minutes;
    }

    private static final Map<LIFETIME, String> lifeTimesList = Map.of(
            TEN_MINUTES,"10 минут",
            ONE_HOUR,"10 минут",
            THREE_HOURS,"3 часа",
            ONE_DAY,"1 день",
            ONE_WEEK,"1 неделя",
            ONE_MONTH,"1 месяц",
            NO_LIMIT,"Без ограничения"

    );

    public static Map<LIFETIME, String> getLifTimes() {
        return lifeTimesList;
    }

}
