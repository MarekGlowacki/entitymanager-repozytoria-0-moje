package pl.javastart.todo;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.javastart.todo.dto.*;
import pl.javastart.todo.exception.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Long saveTask(TaskDto task) {
        Task taskToSave = new Task(task.getTitle(), task.getDescription(), task.getPriority());
        Task savedTask = taskRepository.save(taskToSave);
        return savedTask.getId();
    }

    public Optional<String> getTaskInfo(Long taskId) {
        return taskRepository.findById(taskId).map(Task::toString);
    }

    @Transactional
    public LocalDateTime startTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        if (task.getStartTime() != null) {
            throw new TaskAlreadyStartedException();
        }
        task.setStartTime(LocalDateTime.now());
        return task.getStartTime();
    }

    @Transactional
    public TaskDurationDto endTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
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