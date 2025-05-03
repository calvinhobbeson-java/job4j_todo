package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface Store {

    Task create(Task task);

    boolean update(Task task);

    boolean deleteById(int id);

    Collection<Task> findAll();

    Optional<Task> findById(int id);

    Collection<Task> findNew();

    Collection<Task> findDone();

    boolean setDoneById(int id);
}
