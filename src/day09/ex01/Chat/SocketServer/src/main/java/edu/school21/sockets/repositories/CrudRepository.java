package edu.school21.sockets.repositories;

import java.util.List;

public interface CrudRepository<T> {
    T findById(Long id);
    List<T> findAll();
    void update(T entity);
    void save(T entity);
    void delete(Long id);
}
