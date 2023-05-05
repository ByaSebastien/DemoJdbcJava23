package org.example.repositories;

import org.example.models.entities.Book;

import java.sql.Connection;
import java.util.List;

public interface BaseRepository<TEntity> {

    TEntity getOne(Integer id);

    List<TEntity> getMany();

    TEntity add(TEntity entity, Connection conn);

    TEntity add(TEntity entity);

    boolean update(Integer id, TEntity entity);

    boolean delete(Integer id);
}
