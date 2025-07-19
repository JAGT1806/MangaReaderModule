package com.jagt.reader.shared.security.infrastructure.model;

import com.jagt.reader.shared.security.domain.model.SecurityUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CustomUserDetails extends User {
    public final Long id;
    public final Boolean enabled;

    public CustomUserDetails(SecurityUser user) {
        super(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                mapAuthorities(user.getRoles())
        );

        this.id = user.getId();
        this.enabled = user.isEnabled();
    }


    private static Collection<? extends GrantedAuthority> mapAuthorities(Set<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
