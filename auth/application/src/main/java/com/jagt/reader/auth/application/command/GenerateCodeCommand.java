package com.jagt.reader.auth.application.command;

import com.jagt.reader.auth.domain.model.enums.CodeType;
import com.jagt.reader.user.domain.model.User;

public record GenerateCodeCommand(
        User user,
        CodeType codeType
) {
}
