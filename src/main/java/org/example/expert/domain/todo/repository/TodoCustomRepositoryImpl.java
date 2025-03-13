package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.enums.SearchType;
import org.example.expert.domain.user.entity.QUser;
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
    public List<TodoSearchResponse> findAllBySearch(SearchType searchType, String keyword, LocalDate searchStartDate, LocalDate searchEndDate, Pageable pageable) {

        QTodo todo = QTodo.todo;
        QUser user = QUser.user;
        QManager manager = QManager.manager;
        QComment comment = QComment.comment;

        BooleanBuilder whereBuilder = new BooleanBuilder();
        BooleanBuilder havingBuilder = new BooleanBuilder();

        // 키워드 검색 ( TITLE, NICKNAME )
        if(keyword != null && !keyword.isEmpty()){
            switch (searchType) {
                case TITLE:
                    whereBuilder.and(todo.title.containsIgnoreCase(keyword));
                    break;
                case NICKNAME:
                    whereBuilder.and(manager.user.nickname.containsIgnoreCase(keyword));
                    break;
            }
        }

        // 기간 검색 ( searchStartDate ~ searchEndDate )
        if(searchStartDate != null && searchEndDate != null){
            whereBuilder.and(
                    todo.createdAt.between(
                            searchStartDate.atStartOfDay(),
                            LocalDateTime.of(searchEndDate, LocalTime.MAX).withNano(0)
                    )
            );
        }

        // 기간 검색 ( searchStartDate ~ )
        if(searchStartDate != null && searchEndDate == null){
            havingBuilder.and(
                    todo.createdAt.min().goe(searchStartDate.atStartOfDay())
            );
        }

        // 기간 검색 ( ~ searchEndDate )
        if(searchStartDate == null && searchEndDate != null){
            havingBuilder.and(
                    todo.createdAt.max().loe(LocalDateTime.of(searchEndDate, LocalTime.MAX).withNano(0))
            );
        }

        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                TodoSearchResponse.class,
                                todo.title.as("title"),
                                manager.count().as("managerCnt"),
                                comment.count().as("commentCnt")
                            )
                        )
                        .from(todo)
                        .join(todo.managers, manager)
                        .join(manager.user, user)
                        .leftJoin(todo.comments, comment)
                        .where(whereBuilder)
                        .groupBy(todo.title)
                        .having(havingBuilder)
                        .fetch();
    }
}
