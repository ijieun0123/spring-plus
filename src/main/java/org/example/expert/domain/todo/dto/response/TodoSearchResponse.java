package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TodoSearchResponse {

    private final String title;
    private final Long managerCnt;
    private final Long commentCnt;

    public TodoSearchResponse(String title, Long managerCnt, Long commentCnt) {
        this.title = title;
        this.managerCnt = managerCnt;
        this.commentCnt = commentCnt;
    }
}
