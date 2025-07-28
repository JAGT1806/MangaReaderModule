package com.jagt.reader.user.domain.model;

import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.shared.common.domain.model.value.AuditTimestampsValue;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.domain.model.value.ProfilePicture;
import com.jagt.reader.user.domain.model.value.UserValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private IDValue id;
    private UserValue userValue;
    private AuditTimestampsValue auditTimestamps;
    private ProfilePicture profilePicture;
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
    private boolean enabled;
}
