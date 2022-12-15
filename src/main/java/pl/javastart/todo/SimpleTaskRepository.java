package pl.javastart.todo;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
class SimpleTaskRepository implements TaskRepository {
    private final EntityManager entityManager;
    private static long nextId = 1;

    public SimpleTaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional
    @Override
    public Task save(Task task) {
        task.setId(nextId);
        nextId++;
        entityManager.persist(task);
        return task;
    }
    @Override
    public Optional<Task> findById(Long id) {
        Task task = entityManager.find(Task.class, id);
        return Optional.ofNullable(task);
    }
}
