package ru.petrov.tasksystem.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Schema(description = "Заголовок задачи", example = "Заказать книгу")
    @NotBlank
    String head;
    @Schema(description = "Описание", example = "По два экземпляра на отдел.")
    @NotBlank
    String description;
    @NotNull
    //TODO добавить валидацию перечисления
    TaskStatus status;
    @NotNull
    //TODO добавить валидацию перечисления
    TaskPriority priority;
    @Schema(description = "Комментарий")
    String comment;

    @Schema(description = "Имя автора", example = "user")
    String authorUsername;
    @NotNull
    @Schema(description = "Имя исполнителя", example = "user")
    String executorUsername;
}