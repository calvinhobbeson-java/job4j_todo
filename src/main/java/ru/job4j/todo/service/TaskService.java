package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task create(Task task);

    boolean update(Task task);

    boolean deleteById(int id);

    Collection<TaskDto> findAll();

    Optional<TaskDto> findById(int id);

    Collection<TaskDto> findNew();

    Collection<TaskDto> findDone();

    boolean setDoneById(int id);


}
