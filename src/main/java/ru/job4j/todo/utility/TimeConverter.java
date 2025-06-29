package ru.job4j.todo.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeConverter {
    public static LocalDateTime conversion(LocalDateTime utcTime, String zoneId) {
        return utcTime.atZone(ZoneId.of(zoneId)).toLocalDateTime();
    }
}
