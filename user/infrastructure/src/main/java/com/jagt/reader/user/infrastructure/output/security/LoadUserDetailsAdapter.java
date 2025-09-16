package com.jagt.reader.user.infrastructure.output.security;

import com.jagt.reader.shared.security.domain.model.SecurityUser;
import com.jagt.reader.shared.security.domain.port.output.LoadUserDetailsPort;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LoadUserDetailsAdapter implements LoadUserDetailsPort {
    private final GetUserUseCase getUserUseCase;

    @Override
    public SecurityUser execute(String username) {
        User user = getUserUseCase.execute(username);

        Set<String> authorities = new HashSet<>(user.getRoles().stream().map(role -> role.getName().value()).toList());

        return build(user, authorities);
    }

    private SecurityUser build(User user, Set<String> authorities) {
        return new SecurityUser(user.getId().value(), user.getUserValue().getEmail(), user.getUserValue().getPassword(), user.isEnabled(), authorities);
    }

}
