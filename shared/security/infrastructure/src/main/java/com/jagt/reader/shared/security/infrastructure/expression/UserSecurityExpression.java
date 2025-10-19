package com.jagt.reader.shared.security.infrastructure.expression;

import com.jagt.reader.role.application.port.input.GetRoleUseCase;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import com.jagt.reader.shared.security.domain.exception.SelfRoleModificationException;
import com.jagt.reader.shared.security.infrastructure.expression.util.RoleAction;
import com.jagt.reader.shared.security.infrastructure.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class UserSecurityExpression {
    private final GetRoleUseCase getRoleUseCase;
    private final MessageProvider messageProvider;

    public boolean canAccessUser(Long userId) {
        Authentication auth = getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails))
            return false;
        return userDetails.getId().equals(userId);
    }

    public boolean canModifyRole(Long roleId) {
        return checkRolePermission(roleId, RoleAction.MODIFY);
    }

    public boolean canDeleteRole(Long roleId) {
        return checkRolePermission(roleId, RoleAction.DELETE);
    }

    private Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth : null;
    }


    private boolean checkRolePermission(Long roleId, RoleAction action) {
        Authentication auth = getAuthentication();
        if (auth == null) return false;

        Set<String> userRoles = getUserRoles(auth);

        if (!userRoles.contains("ADMIN")) return false;

        String roleName = getRoleUseCase.execute(IDValue.builder().id(roleId).build()).getName().getName();

        if (userRoles.contains(roleName))
            throw new SelfRoleModificationException(messageProvider.getMessage("role.self" + action.name().toLowerCase() + ".forbidden"));
        return true;
    }

    private Set<String> getUserRoles(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
