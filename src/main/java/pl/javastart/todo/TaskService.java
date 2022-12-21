package pl.javastart.todo;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.javastart.todo.dto.*;
import pl.javastart.todo.exception.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
class TaskService {
    private final DbTaskRepository dbTaskRepository;
    private static long nextId = 1;
    public TaskService(DbTaskRepository taskRepository) {
        this.dbTaskRepository = taskRepository;
    }

    @Transactional
    public Long saveTask(TaskDto task) {
        Task taskToSave = new Task(task.getTitle(), task.getDescription(), task.getPriority());
        taskToSave.setId(nextId);
        Task savedTask = dbTaskRepository.save(taskToSave);
        nextId++;
        return savedTask.getId();
    }

    public Optional<String> getTaskInfo(Long taskId) {
        return dbTaskRepository.findById(taskId).map(Task::toString);
    }

    @Transactional
    public LocalDateTime startTask(Long taskId) {
        Task task = dbTaskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        if (task.getStartTime() != null) {
            throw new TaskAlreadyStartedException();
        }
        task.setStartTime(LocalDateTime.now());
        return task.getStartTime();
    }

    @Transactional
    public TaskDurationDto endTask(Long taskId) {
        Task task = dbTaskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        if (task.getStartTime() == null) {
            throw new TaskNotStartedException();
        } else if (task.getCompletionTime() != null) {
            throw new TaskAlreadyCompletedException();
        }
        task.setCompletionTime(LocalDateTime.now());
        return new TaskDurationDto(task.getStartTime(), task.getCompletionTime());
    }
}