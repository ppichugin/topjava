package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.mealsAdmin.forEach(meal -> save(meal, 1));
        MealsUtil.meals.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        if (meal.isNew()) {
            log.debug("Save new meal for user {}", userId);
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            repository.put(userId, userMeals);
            return meal;
        }
        log.debug("Update meal {} for user {}", meal.getId(), userId);
        // handle case: update, but not present in storage
        Map<Integer, Meal> userMealMap = repository.computeIfPresent(userId, (id, mealMap) -> {
            mealMap.put(meal.getId(), meal);
            return mealMap;
        });
        return userMealMap.get(meal.getId());
    }

    @Override
    public boolean delete(int mealId, int userId) {
        log.debug("Delete meal {} for user {}", mealId, userId);
        return repository.get(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        log.debug("Get meal {} for user {}", mealId, userId);
        return repository.get(userId).get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return getAllFiltered(userId, meal -> true);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals
                    .values()
                    .stream()
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDate).reversed())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}