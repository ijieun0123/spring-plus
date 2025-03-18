package org.example.expert.domain.manager.enums;

import org.example.expert.domain.common.exception.InvalidRequestException;

import java.util.Arrays;

public enum Status {
    SUCCESS, FAIL;

    public static org.example.expert.domain.manager.enums.Status of(String status) {
        return Arrays.stream(org.example.expert.domain.manager.enums.Status.values())
                .filter(r -> r.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 Status"));
    }
}