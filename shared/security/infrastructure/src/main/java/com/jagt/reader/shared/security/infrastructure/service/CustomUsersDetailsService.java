package com.jagt.reader.shared.security.infrastructure.service;

import com.jagt.reader.shared.security.domain.model.SecurityUser;
import com.jagt.reader.shared.security.domain.port.input.LoadUserDetailsPort;
import com.jagt.reader.shared.security.infrastructure.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService {
    private  final LoadUserDetailsPort loadUserDetailsPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = loadUserDetailsPort.execute(username);

        return new CustomUserDetails(user);
    }
}
