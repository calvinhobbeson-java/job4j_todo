package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleUserStore implements UserStore {
    private final CrudRepository crudRepository;

    @Override
    public User save(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional("from User u WHERE u.login = :login and u.password = :password",
                User.class, Map.of("login", login, "password", password));
    }
}
