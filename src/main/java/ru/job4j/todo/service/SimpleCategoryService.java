package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;
import ru.job4j.todo.store.Store;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private final CategoryStore store;

    @Override
    public Collection<Category> findAll() {
        return store.findAll();
    }

    @Override
    public Collection<Category> findAllById(List<Integer> categoryId) {
        return store.findAllById(categoryId);
    }
}
