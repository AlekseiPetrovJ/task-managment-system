package ru.petrov.tasksystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petrov.tasksystem.model.dto.TaskDto;
import ru.petrov.tasksystem.model.exception.TaskNotFound;
import ru.petrov.tasksystem.service.TaskService;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Задачи")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Получить все задачи",
    description = "Администратору доступны все задачи. Пользователю - только те в которых он является " +
            "автором или исполнителем.")
    @GetMapping
    public ResponseEntity<Page<TaskDto>> getTracks(@ParameterObject Pageable pageable) {
        log.info(pageable);
        return new ResponseEntity<>(taskService.findAll(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Получить задачу по id",
            description = "Администратору доступна любая задача. Пользователю - только та в которой он является " +
                    "автором или исполнителем.")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTrackById(@PathVariable Long id) {
        log.info(id);
        return new ResponseEntity<>(taskService.findTaskDtoById(id), HttpStatus.OK);
    }

    @Operation(summary = "Добавить задачу", description = "Автором добавленной задачи будет назначен текущий пользователь.")
    @PostMapping
    public ResponseEntity<TaskDto> saveTask(@RequestBody @Valid TaskDto taskDto) {
        log.info(taskDto);
        return new ResponseEntity<>(taskService.save(taskDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить задачу",
            description = "Администратор и автор могут изменять любые параметры в задаче. " +
                    "Пользователи не администраторы могут изменять только статус задачи и комментарий " +
                    "только в задачах в которых они являются исполнителями.")
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody @Valid TaskDto taskDto, @PathVariable Long id) {
        log.info("taskDto: {} id:{}", taskDto, id);
        TaskDto updatedTask = taskService.update(id, taskDto);
        if(updatedTask!=null){
            log.info("updatedTask: {}, HttpStatus: OK", updatedTask);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            log.info("FORBIDDEN");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFound.class)
    public ResponseEntity handleException(TaskNotFound exception) {
        log.error(exception);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}