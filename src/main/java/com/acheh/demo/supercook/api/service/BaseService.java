package com.acheh.demo.supercook.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Base service interface
 * @param <E> entity generic type
 * @param <ID> entity id generic type
 */
public interface BaseService<E, ID> {

    /**
     * Find entity by id
     * @param id entity identifier
     * @return entity
     */
    E findById(ID id);

    /**
     * Save entity
     * @param entity entity to save
     * @return saved entity
     */
    E save(E entity);

    /**
     * Update entity
     * @param entity entity to update
     * @return updated entity
     */
    E update(ID id, E entity);

    /**
     * Delete entity by id
     * @param id entity identifier
     */
    void deleteById(ID id);

    /**
     * Find all entities based on search criteria
     * @param pageable pagination information
     * @return page of entities
     */
    Page<E> find(String search, Pageable pageable);

}
