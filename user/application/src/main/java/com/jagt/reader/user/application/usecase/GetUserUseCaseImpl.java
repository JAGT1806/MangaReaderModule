package com.jagt.reader.user.application.usecase;

import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.user.application.mapper.UserApplicationMapper;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.application.port.input.ProfilePictureUseCase;
import com.jagt.reader.user.application.query.GetUserFilterQuery;
import com.jagt.reader.user.domain.exception.UserEmailNotFoundException;
import com.jagt.reader.user.domain.exception.UserNotFoundException;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.model.value.ProfilePicture;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final UserPersistencePort port;
    private final UserApplicationMapper mapper;
    private final ProfilePictureUseCase profilePictureUseCase;

    @Override
    public Pagination<User> execute(GetUserFilterQuery query) {
        List<User> users = port.findByFilters(query.username().value(), query.email().value(), query.role().value(), query.enabled(), query.pagination().offset(), query.pagination().limit());
        long total = port.countByFilters(query.username().value(), query.email().value(), query.role().value(), query.enabled());

        return mapper.toPagination(users, query.pagination().offset(), query.pagination().limit(), total);
    }

    @Override
    public User execute(Long userId) {
        User user = port.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));

        if(!user.getProfilePicture().isDefault()) {
            try {
                user.getProfilePicture().setUrl(profilePictureUseCase.get(userId));
            } catch (Exception e) {
                user.getProfilePicture().setUrl(ProfilePicture.defaultPicture().getUrl());
            }
        } else {
            user.getProfilePicture().setUrl(ProfilePicture.defaultPicture().getUrl());
        }

        return user;
    }

    @Override
    public User execute(String email) {
        return port.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException(String.valueOf(email)));
    }
}
