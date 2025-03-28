package org.example.expert.config;

import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final AuthUser authUser;

    public JwtAuthenticationToken(AuthUser authUser) {
        super(List.of(new SimpleGrantedAuthority(authUser.getUserRole().name())));
        this.authUser = authUser;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authUser;
    }
}
