package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Dao<T> {
    T add(T item);

    Meal get(int id);

    T update(T item);

    void delete(int id);

    List<T> getAll();
}