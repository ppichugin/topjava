package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectionMealDao implements Dao<Meal> {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1000);

    public CollectionMealDao() {
        MealsUtil.meals.forEach(this::add);
    }

    @Override
    public Meal add(Meal item) {
        int id = getNextId();
        Meal newMeal = new Meal(id, item.getDateTime(), item.getDescription(), item.getCalories());
        return storage.computeIfAbsent(id, integer -> newMeal);
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public Meal update(Meal item) {
        Meal existing = new Meal(item.getId(), item.getDateTime(), item.getDescription(), item.getCalories());
        return storage.computeIfPresent(item.getId(), (integer, meal) -> existing);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    private int getNextId() {
        return idGenerator.incrementAndGet();
    }
}