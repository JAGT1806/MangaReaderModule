package com.jagt.reader.shared.security.infrastructure.service;

import com.jagt.reader.shared.security.domain.port.output.AuthenticationManagerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManagerPort {
    private final AuthenticationManager authenticationManager;

    @Override
    public void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username.toLowerCase(), password));
    }
}
