package ru.petrov.tasksystem.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.petrov.tasksystem.model.dto.TaskDto;
import ru.petrov.tasksystem.repository.TaskRepository;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public Page<TaskDto> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(track -> modelMapper.map(track, TaskDto.class));
    }
}