package com.jagt.reader.shared.common.domain.model.value;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IDValue {
    Long id;

    public Long value() {
        return this.id;
    }
}
