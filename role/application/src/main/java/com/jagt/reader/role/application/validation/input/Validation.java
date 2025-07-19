package com.jagt.reader.role.application.validation.input;

import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validation {
    private final MessageProvider messageProvider;

    public void validateNull(IDValue idValue) {
        if(idValue == null || idValue.value() == null)
            throw new IllegalArgumentException(messageProvider.getMessage("id.null"));
    }
}
