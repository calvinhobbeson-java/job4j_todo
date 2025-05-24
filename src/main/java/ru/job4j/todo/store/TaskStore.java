package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore implements Store {
    private final SessionFactory sf;


    @Override
    public Task create(Task task) {
        Session session = sf.openSession();
        Task emptyTask = new Task();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            int updated = session.createQuery(
                            "UPDATE Task SET description = :description, created = :created, done = :done "
                                    + "WHERE id = :id")
                    .setParameter("description", task.getDescription())
                    .setParameter("created", task.getCreated())
                    .setParameter("done", task.getDone())
                    .setParameter("id", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            return updated > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            int deleted = session.createQuery(
                            "DELETE Task WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return deleted > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        List<Task> result = List.of();
        try {
            session.beginTransaction();
            result = session.createQuery(
                    "from Task ORDER by id", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Task task = null;
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                            "from Task t WHERE t.id = :id", Task.class)
                    .setParameter("id", id);
            task = query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.ofNullable(task);
    }

    @Override
    public Collection<Task> findNew() {
        Session session = sf.openSession();
        List<Task> result = List.of();
        try {
            session.beginTransaction();
            result = session.createQuery(
                    "from Task WHERE done = false", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Collection<Task> findDone() {
        Session session = sf.openSession();
        List<Task> result = List.of();
        try {
            session.beginTransaction();
            result = session.createQuery(
                    "from Task WHERE done = true", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean setDoneById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            int updated = session.createQuery(
                            "UPDATE Task SET done = true "
                                    + "WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return updated > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }
}