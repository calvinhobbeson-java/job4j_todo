package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class SimpleCategoryStore implements CategoryStore {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }

    @Override
    public Collection<Category> findAllById(List<Integer> categoryIds) {
        return crudRepository.query(
                "from Category t WHERE t.id IN (:categoryIds)",
                Category.class,
                Map.of("categoryIds", categoryIds)
        );
    }
}
