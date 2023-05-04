package org.example.repositories;

import org.example.models.entities.User;

public interface UserRepository extends BaseRepository<User> {

    User findByLogin(String login);
}
