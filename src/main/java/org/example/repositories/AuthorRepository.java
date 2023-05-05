package org.example.repositories;

import org.example.models.entities.Author;

import java.sql.Connection;
import java.util.List;

public interface AuthorRepository extends BaseRepository<Author> {

    List<Author> getByKeyword(String keyword);
}
