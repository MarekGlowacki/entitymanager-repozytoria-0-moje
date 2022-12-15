package pl.javastart.todo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class SimpleTaskRepository implements TaskRepository {
    private final Map<Long, Task> tasks = new HashMap<>();
    private static long nextId = 1;

    @Override
    public Task save(Task task) {
        task.setId(nextId);
        tasks.put(nextId, task);
        nextId++;
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }
}
