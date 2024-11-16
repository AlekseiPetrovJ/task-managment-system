package ru.petrov.tasksystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petrov.tasksystem.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}