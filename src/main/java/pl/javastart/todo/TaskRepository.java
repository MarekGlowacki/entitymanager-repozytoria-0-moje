package pl.javastart.todo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByStartTimeIsNullOrderByPriorityDesc();
    List<Task> findAllByCompletionTimeIsNotNullOrderByCompletionTimeDesc();
}