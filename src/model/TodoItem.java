package model;

import java.time.LocalDateTime;

/**
 * TodoItem entity for dashboard to-do list
 */
public class TodoItem {
    private int id;
    private int userId;
    private String task;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    // Constructors
    public TodoItem() {
    }

    public TodoItem(int userId, String task) {
        this.userId = userId;
        this.task = task;
        this.isCompleted = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String toString() {
        return task;
    }
}

