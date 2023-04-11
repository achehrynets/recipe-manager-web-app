package com.acheh.demo.supercook.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<E, ID> {

    E getById(ID id);

    E save(E entity);

    void deleteById(ID id);

    Page<E> find(String search, Pageable pageable);

}
