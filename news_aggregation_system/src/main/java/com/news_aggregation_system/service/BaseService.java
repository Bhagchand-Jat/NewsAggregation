package com.news_aggregation_system.service;

import java.util.List;

public interface BaseService<T, ID> {
    T create(T dto);

    T update(ID id, T dto);

    T getById(ID id);

    List<T> getAll();

    void delete(ID id);
}
