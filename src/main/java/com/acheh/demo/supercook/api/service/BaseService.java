package com.acheh.demo.supercook.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Base service interface
 * @param <E> entity generic type
 * @param <ID> entity id generic type
 */
public interface BaseService<E, ID> {

    E getById(ID id);

    E save(E entity);

    void deleteById(ID id);

    Page<E> find(String search, Pageable pageable);

}
