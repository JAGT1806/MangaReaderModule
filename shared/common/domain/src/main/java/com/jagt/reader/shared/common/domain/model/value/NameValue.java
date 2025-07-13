package com.jagt.reader.shared.common.domain.model.value;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NameValue {
    String name;

    public String value() {
        return this.name;
    }
}
