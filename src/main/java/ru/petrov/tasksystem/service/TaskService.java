package ru.petrov.tasksystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petrov.tasksystem.model.Task;
import ru.petrov.tasksystem.model.User;
import ru.petrov.tasksystem.model.dto.TaskDto;
import ru.petrov.tasksystem.model.exception.TaskNotFound;
import ru.petrov.tasksystem.repository.TaskRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public Page<TaskDto> findAll(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.isAdmin()) {
            return taskRepository.findAll(pageable)
                    .map(task -> modelMapper.map(task, TaskDto.class));
        } else {
            return taskRepository.findAllByAuthorOrExecutor(pageable, currentUser, currentUser)
                    .map(task -> modelMapper.map(task, TaskDto.class));
        }
    }

    @Transactional
    public TaskDto save(TaskDto taskDto) {
        Task newTask = modelMapper.map(taskDto, Task.class);
        newTask.setId(null);
        newTask.setAuthor(userService.getCurrentUser());
        newTask.setExecutor(userService.getByUsername(taskDto.getExecutorUsername()));
        return modelMapper.map(taskRepository.save(newTask), TaskDto.class);
    }

    public TaskDto findTaskDtoById(Long id) {
        return modelMapper.map(findTaskById(id), TaskDto.class);
    }

    public Task findTaskById(Long id) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.isAdmin()) {
            return taskRepository.findById(id).orElseThrow(() ->
                    new TaskNotFound(String.format("task with ID:%s not found", id)));
        } else {
            return taskRepository.findTaskByIdAndUserId(id, currentUser.getId()).orElseThrow(() ->
                    new TaskNotFound(String.format("task with ID:%s not found", id)));
        }
    }

    /**
     * Обновляет задачу если такая существует и если достаточно прав.
     *
     * @param idTask  - обновляемой задачи
     * @param taskDto - если не достаточно прав, то возвращается null
     * @return
     */
    @Transactional
    public TaskDto update(Long idTask, TaskDto taskDto) {
        Task taskById = findTaskById(idTask);
        User currentUser = userService.getCurrentUser();
        if (currentUser.isAdmin() || taskById.getAuthor().equals(currentUser)) {
            Task updatedTask = modelMapper.map(taskDto, Task.class);
            updatedTask.setId(idTask);
            updatedTask.setAuthor(currentUser);
            updatedTask.setExecutor(userService.getByUsername(taskDto.getExecutorUsername()));
            return modelMapper.map(taskRepository.save(updatedTask), TaskDto.class);
        } else if (taskById.getExecutor().equals(currentUser)) {
            taskById.setStatus(taskDto.getStatus());
            taskById.setComment(taskDto.getComment());
            return modelMapper.map(taskRepository.save(taskById), TaskDto.class);
        } else {
            return null;
        }
    }
}