package com.minakov.service;

import java.util.List;

public interface Service<T> {

    T get(int id);

    List<T> getAll();

    void save(T entity);

    void delete(int id);
}
