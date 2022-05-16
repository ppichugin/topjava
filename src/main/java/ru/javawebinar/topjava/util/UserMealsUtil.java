package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

        System.out.println(filteredByOneStream(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> mealWithExcessList = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        for (UserMeal meal : meals) {
            map.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        for (UserMeal meal : meals) {
            LocalDateTime currentDateTime = meal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(currentDateTime.toLocalTime(), startTime, endTime)) {
                mealWithExcessList.add(new UserMealWithExcess(currentDateTime, meal.getDescription(),
                        meal.getCalories(), (map.get(currentDateTime.toLocalDate()) > caloriesPerDay)));
            }
        }
        return mealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> datesMap = meals.stream()
                .collect(Collectors.toMap(
                        meal -> meal.getDateTime().toLocalDate(),
                        UserMeal::getCalories, Integer::sum));
        return meals
                .stream()
                .filter(time -> TimeUtil.isBetweenHalfOpen(time.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> {
                    LocalDateTime currentDateTime = meal.getDateTime();
                    return new UserMealWithExcess(currentDateTime, meal.getDescription(), meal.getCalories(),
                            (datesMap.get(currentDateTime.toLocalDate()) > caloriesPerDay));
                })
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByOneStream(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals
                .stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()))
                .values()
                .stream()
                .flatMap(mealForCurrentDay -> {
                    int sumOfDailyCalories = mealForCurrentDay
                            .stream()
                            .mapToInt(UserMeal::getCalories)
                            .sum();
                    boolean isExceeded = sumOfDailyCalories > caloriesPerDay;
                    return mealForCurrentDay
                            .stream()
                            .filter(m -> TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime))
                            .map(m -> new UserMealWithExcess(m.getDateTime(), m.getDescription(), m.getCalories(), isExceeded));
                })
                .collect(Collectors.toList());
    }
}