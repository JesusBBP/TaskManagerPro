package org.example.service;

import org.example.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskService {
    private List<Task> tasks;
    private Long nextId;

    public TaskService() {
        this.tasks = new ArrayList<>();
        this.nextId = 1L;
    }

    public Task createTask(String title, String description) {
        Task task = new Task(nextId++, title, description);
        tasks.add(task);
        return task;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Optional<Task> getTaskById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public boolean deleteTask(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }

    public boolean completeTask(Long id) {
        Optional<Task> task = getTaskById(id);
        if (task.isPresent()) {
            task.get().setCompleted(true);
            return true;
        }
        return false;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isCompleted)
                .collect(java.util.stream.Collectors.toList());
    }
    public int deleteCompletedTasks() {
        int initialSize = tasks.size();
        tasks.removeIf(Task::isCompleted);
        return initialSize - tasks.size(); // Retorna cuántas eliminó
    }
    public List<Task> getTasksByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return tasks.stream()
                .filter(task -> ids.contains(task.getId()))
                .collect(Collectors.toList());
    }
    public boolean hasTaskWithTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }

        return tasks.stream()
                .anyMatch(task -> task.getTitle().equalsIgnoreCase(title.trim()));
    }

}
