package com.jagt.reader.auth.domain.model;

import com.jagt.reader.shared.common.domain.model.value.IDValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    String tokenType;
    String accessToken;
    String refreshToken;
}
