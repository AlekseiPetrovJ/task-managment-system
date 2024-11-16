package ru.petrov.tasksystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.petrov.tasksystem.model.dto.TaskDto;
import ru.petrov.tasksystem.service.TaskService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Получить все задачи")
    @GetMapping
    public ResponseEntity<Page<TaskDto>> getTracks(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(taskService.findAll(pageable), HttpStatus.OK);
    }
}