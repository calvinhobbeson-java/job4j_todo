package ru.job4j.todo.service;

import org.apache.tomcat.util.descriptor.web.SecurityRoleRef;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.Store;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class  SimpleTaskService implements TaskService {

    private final Store store;

    public SimpleTaskService(Store store) {
        this.store = store;
    }

    @Override
    public Task create(Task task) {
        return store.create(task);
    }

    @Override
    public boolean update(Task task) {
        return store.update(task);
    }

    @Override
    public boolean deleteById(int id) {
        return store.deleteById(id);
    }

    @Override
    public Collection<TaskDto> findAll() {
        return tasksToDtos(store.findAll());
    }

    @Override
    public Optional<TaskDto> findById(int id) {
        return store.findById(id)
                .map(task -> new TaskDto(task.getId(), task.getUser().getId(), task.getDescription()
                        , task.getCreated(), task.getDone(), task.getPriority().getName()
                        , task.getCategories().stream().map(Category::getName).collect(Collectors.joining(", "))));
    }

    @Override
    public Collection<TaskDto> findNew() {
        return tasksToDtos(store.findNew());
    }

    @Override
    public Collection<TaskDto> findDone() {
        return tasksToDtos(store.findNew());
    }

    @Override
    public boolean setDoneById(int id) {
        return store.setDoneById(id);
    }

    private List<TaskDto> tasksToDtos(Collection<Task> taskCollection) {
        return taskCollection.stream()
                .map(task -> new TaskDto(task.getId(), task.getUser().getId(), task.getDescription()
                        , task.getCreated(), task.getDone(), task.getPriority().getName()
                , task.getCategories().stream().map(Category::getName).collect(Collectors.joining(", "))))
                .toList();
    }
}
