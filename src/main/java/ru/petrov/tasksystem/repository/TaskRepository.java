package ru.petrov.tasksystem.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.petrov.tasksystem.model.Task;
import ru.petrov.tasksystem.model.User;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByAuthorOrExecutor(Pageable pageable, User author, User executor);

    @Query("SELECT t FROM Task t WHERE t.id = :taskId AND (t.author.id = :userId OR t.executor.id = :userId)")
    Optional<Task> findTaskByIdAndUserId(@Param("taskId") Long taskId, @Param("userId") Long userId);
}