package com.jagt.reader.user.domain.model.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserValue {
    private String username;
    private String email;
    private String password;
}
