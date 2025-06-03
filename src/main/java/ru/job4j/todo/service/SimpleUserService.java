package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private final UserStore store;

    public SimpleUserService(UserStore store) {
        this.store = store;
    }

    @Override
    public User save(User user) {
        return store.save(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return store.findByLoginAndPassword(login, password);
    }
}
