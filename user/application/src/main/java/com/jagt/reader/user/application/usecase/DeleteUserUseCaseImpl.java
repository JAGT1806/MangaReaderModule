package com.jagt.reader.user.application.usecase;

import com.jagt.reader.role.application.validation.input.Validation;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.application.port.input.DeleteAllFavoritesByUserUseCase;
import com.jagt.reader.user.application.port.input.DeleteUserUseCase;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserPersistencePort persistencePort;
    private final Validation validation;
    private final GetUserUseCase getUserUseCase;
    private final DeleteAllFavoritesByUserUseCase deleteAllFavoritesByUserUseCase;

    @Override
    public void execute(IDValue userId) {
        validation.validateNull(userId);
        getUserUseCase.execute(userId.getId());
        deleteAllFavoritesByUserUseCase.execute(userId);
        persistencePort.deleteById(userId.getId());
    }
}
