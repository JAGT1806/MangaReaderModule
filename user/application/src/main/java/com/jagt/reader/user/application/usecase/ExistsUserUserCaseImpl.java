package com.jagt.reader.user.application.usecase;

import com.jagt.reader.user.application.port.input.ExistsUserUserCase;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExistsUserUserCaseImpl implements ExistsUserUserCase {
    private final UserPersistencePort userPersistencePort;

    @Override
    public boolean executeByEmail(String email) {
        return userPersistencePort.existsByEmail(email.toLowerCase());
    }
}
