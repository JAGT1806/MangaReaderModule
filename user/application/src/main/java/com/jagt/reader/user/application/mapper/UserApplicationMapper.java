package com.jagt.reader.user.application.mapper;

import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.shared.common.domain.model.value.AuditTimestampsValue;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.application.command.CreateUserCommand;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.model.value.UserValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserApplicationMapper {

    @Mapping(target = "data", source = "users")
    Pagination<User> toPagination(List<User> users, int offset, int limit, long total);

    default User toDomain(CreateUserCommand command) {
        return User.builder()
                .userValue(
                        UserValue.builder()
                                .username(command.username().value())
                                .email(command.email().value().toLowerCase())
                                .password(command.password())
                                .build()
                )
                .auditTimestamps(AuditTimestampsValue.now())
                .build();
    }

    IDValue toValue(Long id);

}
