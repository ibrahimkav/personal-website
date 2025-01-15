package com.personal.portfolio.service.base;

import com.personal.portfolio.entity.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T extends BaseEntity, ID> {
    T findById(ID id);
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    T save(T entity);
    void delete(ID id);
    void softDelete(ID id);
} 