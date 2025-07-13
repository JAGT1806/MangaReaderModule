package com.jagt.reader.shared.common.domain.model.value;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IDValue {
    long id;

    public long value() {
        return this.id;
    }
}
