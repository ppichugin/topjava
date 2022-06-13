package ru.javawebinar.topjava.util;


import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, T firstItem, T lastItem) {
        return lt.compareTo(firstItem) >= 0 && lt.compareTo(lastItem) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseDate(String dateStr) {
        return StringUtils.hasLength(dateStr) ? LocalDate.parse(dateStr) : null;
    }

    public static LocalTime parseTime(String timeStr) {
        return StringUtils.hasLength(timeStr) ? LocalTime.parse(timeStr) : null;
    }
}