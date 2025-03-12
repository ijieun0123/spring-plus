package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoCustomRepository {

    Optional<Todo> findByIdWithUser(Long todoId);

    List<Todo> findAllBySearch(String keyword, LocalDate searchStartDate, LocalDate searchEndDate, Pageable pageable);

}
