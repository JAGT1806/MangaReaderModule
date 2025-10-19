package com.jagt.reader.user.infrastructure.input.rest.response;

import java.util.List;

public record UserListResponse(
        List<UserResponse> data,
        int offset,
        int limit,
        long total
) {
}
