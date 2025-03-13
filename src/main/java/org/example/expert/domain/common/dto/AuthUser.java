package org.example.expert.domain.common.dto;

import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {

    private final Long userId;
    private final String email;
    private final String nickname;
    private final UserRole userRole;

    public AuthUser(Long userId, String email, String nickname, UserRole role) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.userRole = role;
    }
}
