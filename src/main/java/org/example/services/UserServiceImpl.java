package org.example.services;

import org.example.exception.EntityNotFoundException;
import org.example.models.entities.User;
import org.example.repositories.UserRepository;
import org.example.repositories.UserRepositoryImpl;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    public User register(User user) {

        if (user.getUsername().trim().equals(""))
            throw new RuntimeException();
        if (user.getPassword().trim().equals(""))
            throw new RuntimeException();

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        return userRepository.add(user);
    }

    public User login(String login, String pwd) {

        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new EntityNotFoundException();
        }

        if (!BCrypt.checkpw(pwd, user.getPassword())) {
            throw new RuntimeException();
        }

        return user;

    }
}












