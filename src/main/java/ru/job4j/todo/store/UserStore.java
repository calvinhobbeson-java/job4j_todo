package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserStore {

    User save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);
}
