package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;

@Repository
@AllArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(todo)
                .leftJoin(todo.user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }

    @Override
    public List<Todo> findAllBySearch(String keyword, LocalDate searchStartDate, LocalDate searchEndDate, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(todo)
                .where(
                        todo.title.containsIgnoreCase(keyword)
                                .or(todo.user.nickname.containsIgnoreCase(keyword))
                )
                .where(searchDateFilter(searchStartDate, searchEndDate))
                .orderBy(todo.createdAt.desc())
                .fetch();
    }

    // 날짜 필터
    private BooleanExpression searchDateFilter(LocalDate searchStartDate, LocalDate searchEndDate) {

        //goe, loe 사용
        BooleanExpression isGoeStartDate = todo.createdAt.goe(LocalDateTime.of(searchStartDate, LocalTime.MIN));
        BooleanExpression isLoeEndDate = todo.createdAt.loe(LocalDateTime.of(searchEndDate, LocalTime.MAX).withNano(0));

        return Expressions.allOf(isGoeStartDate, isLoeEndDate);
    }
}
