package ru.javawebinar.topjava.dao;

import java.util.List;

public interface DAO<T> {
    void addItem(T item);       //CREATE
    T getItem(Integer id);      //READ
    void updateItem(T item);    //UPDATE
    void deleteItem(Integer id);    //DELETE
    List<T> getAll();
    int size();
}