package com.minakov.dao;

import java.util.List;

public interface DAO<T> {

    T get(int id);

    List<T> getAll();

    void save(T entity);

    void update(int id, T entity);

    void delete(int id);
}
