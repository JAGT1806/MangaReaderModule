package com.jagt.reader.auth.infrastructure.input.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    String tokenType;
    String accessToken;
    String refreshToken;
}
