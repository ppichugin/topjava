package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final Meal userMeal1;
    public static final Meal userMeal2;
    public static final Meal userMeal3;
    public static final Meal userMeal4;
    public static final Meal userMeal5;
    public static final Meal userMeal6;
    public static final Meal userMeal7;
    public static final Meal adminMeal1;
    public static final Meal adminMeal2;
    public static final Meal adminMeal3;
    public static final LocalDateTime DUPLICATE_DATE_TIME;
    public static final int ID_TO_DELETE;
    public static final int ID_TO_GET;
    public static final int ID_TO_UPDATE;
    public static final int OTHER_PERSON_MEAL_ID = START_SEQ + 13;
    public static final int NOT_FOUND = START_SEQ - 1;
    public static final LocalDate START_DATE = LocalDate.of(2022, Month.JUNE, 17);
    public static final LocalDate END_DATE = LocalDate.of(2022, Month.JUNE, 17);

    static {
        userMeal1 = new Meal(START_SEQ + 3, LocalDateTime.of(2022, Month.JUNE, 17, 10, 0), "Завтрак", 500);
        userMeal2 = new Meal(START_SEQ + 4, LocalDateTime.of(2022, Month.JUNE, 17, 13, 0), "Обед", 1000);
        userMeal3 = new Meal(START_SEQ + 5, LocalDateTime.of(2022, Month.JUNE, 17, 20, 0), "Ужин", 500);
        userMeal4 = new Meal(START_SEQ + 6, LocalDateTime.of(2022, Month.JUNE, 18, 0, 0), "Еда на граничное значение", 100);
        userMeal5 = new Meal(START_SEQ + 7, LocalDateTime.of(2022, Month.JUNE, 18, 10, 0), "Завтрак", 1000);
        userMeal6 = new Meal(START_SEQ + 8, LocalDateTime.of(2022, Month.JUNE, 18, 13, 0), "Обед", 500);
        userMeal7 = new Meal(START_SEQ + 9, LocalDateTime.of(2022, Month.JUNE, 18, 20, 0), "Ужин", 410);
        adminMeal1 = new Meal(START_SEQ + 10, LocalDateTime.of(2022, Month.JUNE, 19, 8, 0), "Завтрак админ", 300);
        adminMeal2 = new Meal(START_SEQ + 11, LocalDateTime.of(2022, Month.JUNE, 19, 14, 0), "Обед админ", 700);
        adminMeal3 = new Meal(START_SEQ + 12, LocalDateTime.of(2022, Month.JUNE, 19, 19, 0), "Ужин админ", 1000);
        DUPLICATE_DATE_TIME = adminMeal3.getDateTime();
        ID_TO_DELETE = userMeal3.getId();
        ID_TO_GET = userMeal1.getId();
        ID_TO_UPDATE = userMeal2.getId();
    }

    public static Meal getNew() {
        return new Meal(null,
                LocalDateTime.of(2022, Month.JUNE, 19, 10, 0),
                "New meal", 550);
    }

    public static Meal getUpdated() {
        Meal upd = new Meal(userMeal2);
        upd.setDateTime(LocalDateTime.of(2022, Month.FEBRUARY, 10, 12, 0));
        upd.setDescription("Updated user meal");
        upd.setCalories(800);
        return upd;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}