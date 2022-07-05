package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.adminMeals;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User actual = service.getUserAndMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(actual, admin);
        MEAL_MATCHER.assertMatch(actual.getMeals(), adminMeals);
    }

    @Test
    public void getWithMealNotOwn() {
        User actual = service.getUserAndMeals(GUEST_ID);
        USER_MATCHER.assertMatch(actual, guest);
        Assert.assertTrue(actual.getMeals().isEmpty());
    }

    @Test
    public void getWithMealNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getUserAndMeals(
                        UserTestData.NOT_FOUND));
    }
}