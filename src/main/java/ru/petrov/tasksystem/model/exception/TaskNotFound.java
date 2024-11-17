package ru.petrov.tasksystem.model.exception;

public class TaskNotFound extends RuntimeException{
    public TaskNotFound() {
    }

    public TaskNotFound(String message) {
        super(message);
    }
}
