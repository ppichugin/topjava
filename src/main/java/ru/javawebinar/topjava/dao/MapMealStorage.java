package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealStorage implements Dao<Meal> {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1000);

    public MapMealStorage() {
        for (Meal meal : MealsUtil.meals) {
            add(meal);
        }
    }

    @Override
    public Meal add(Meal item) {
        int id = getNextId();
        storage.put(id, new Meal(id, item));
        return storage.get(id);
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public Meal update(Meal item) {
        storage.replace(item.getId(), item);
        return storage.get(item.getId());
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