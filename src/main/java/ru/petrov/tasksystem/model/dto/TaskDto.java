package ru.petrov.tasksystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.petrov.tasksystem.model.TaskPriority;
import ru.petrov.tasksystem.model.TaskStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    Long id;
    String head;
    String description;
    TaskStatus status;
    TaskPriority priority;
    String comment;
    String authorUsername;
    String executorUsername;
}