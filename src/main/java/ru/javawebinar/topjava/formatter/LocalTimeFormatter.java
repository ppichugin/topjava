package ru.javawebinar.topjava.formatter;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public final class LocalTimeFormatter implements Formatter<LocalTime> {

    @Override
    @NotNull
    public LocalTime parse(@NotNull String text, @NotNull Locale locale) {
        return Objects.requireNonNull(parseLocalTime(text));
    }

    @Override
    @NotNull
    public String print(@NotNull LocalTime localTime, @NotNull Locale locale) {
        return localTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
