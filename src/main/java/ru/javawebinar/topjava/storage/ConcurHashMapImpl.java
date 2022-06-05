package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurHashMapImpl implements MealDao {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    public ConcurHashMapImpl() {
        for (Meal meal : MealsUtil.meals) {
            storage.put(meal.getId(), meal);
        }
    }

    @Override
    public void addItem(Meal item) {
        storage.put(item.getId(), item);
    }

    @Override
    public Meal getItem(Integer id) {
        return storage.get(id);
    }

    @Override
    public void updateItem(Meal item) {
        storage.put(item.getId(), item);
    }

    @Override
    public void deleteItem(Integer id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
