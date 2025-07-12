package com.jagt.reader.shared.security.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class SecurityUser {
    private final Long id;
    private final String username;
    private final String password;
    private final boolean enabled;
    private final Set<String> roles;
}
