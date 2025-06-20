package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryStore {

    Collection<Category> findAll();

    Collection<Category> findAllById(List<Integer> categoryId);
}
