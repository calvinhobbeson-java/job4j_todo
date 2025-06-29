package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CrudRepository;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class TaskStore implements Store {
    private final CrudRepository crudRepository;


    @Override
    public Task create(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean update(Task task) {
        try {
            crudRepository.run(session -> session.merge(task));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run("DELETE from Task WHERE id = :id", Map.of("id", id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query("from Task t LEFT JOIN FETCH t.priority LEFT JOIN FETCH t.categories", Task.class);
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional("from Task t WHERE t.id = :id", Task.class, Map.of("id", id));
    }

    @Override
    public Collection<Task> findNew() {
        return crudRepository.query("from Task t LEFT JOIN FETCH t.priority LEFT JOIN FETCH t.categories WHERE done = false", Task.class);
    }

    @Override
    public Collection<Task> findDone() {
        return crudRepository.query("from Task t LEFT JOIN FETCH t.priority LEFT JOIN FETCH t.categories WHERE done = true", Task.class);
    }

    @Override
    public boolean setDoneById(int id) {
        try {
            crudRepository.optional("UPDATE Task SET done = true WHERE id = :id", Task.class, Map.of("id", id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}