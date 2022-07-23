package ru.javawebinar.topjava.formatter;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public final class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    @NotNull
    public LocalDate parse(@NotNull String text, @NotNull Locale locale) {
        return Objects.requireNonNull(parseLocalDate(text));
    }

    @Override
    @NotNull
    public String print(@NotNull LocalDate localDate, @NotNull Locale locale) {
        return localDate.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
