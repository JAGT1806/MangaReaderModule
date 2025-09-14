package com.jagt.reader.auth.domain.model;

import com.jagt.reader.auth.domain.model.enums.CodeType;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeSecurity {
    private IDValue id;
    private String code;
    private CodeType type;
    private User user;
    private LocalDateTime expiration;
    private boolean used;
}
