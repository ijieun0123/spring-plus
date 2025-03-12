package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class TodoSearchResponse {

    private final String title;
    private final Integer managerCnt;
    private final Integer commentCnt;

    public TodoSearchResponse(String title, Integer managerCnt, Integer commentCnt) {
        this.title = title;
        this.managerCnt = managerCnt;
        this.commentCnt = commentCnt;
    }
}
