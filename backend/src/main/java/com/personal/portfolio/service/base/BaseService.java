package com.personal.portfolio.service.base;

import java.util.List;

public interface BaseService<T, ID> {
    T save(T entity);
    
    T update(ID id, T entity);
    
    void delete(ID id);
    
    T findById(ID id);
    
    List<T> findAll();
    
    boolean existsById(ID id);
} 