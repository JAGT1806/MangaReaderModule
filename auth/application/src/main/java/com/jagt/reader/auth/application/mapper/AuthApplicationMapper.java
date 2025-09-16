package com.jagt.reader.auth.application.mapper;

import com.jagt.reader.auth.domain.model.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthApplicationMapper {
    Token toDomain(String tokenType, String accessToken, String refreshToken);
}
