package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getMealWithUser() {
        Meal actual = service.getMealAndUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(actual, adminMeal1);
        USER_MATCHER.assertMatch(actual.getUser(), admin);
    }

    @Test
    public void getMealNotUser() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getMealAndUser(ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void getMealNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getMealAndUser(NOT_FOUND, NOT_FOUND));
    }
}
