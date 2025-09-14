package com.jagt.reader.auth.domain.model.enums;

import lombok.Getter;

@Getter
public enum CodeType {
    ACTIVATION(6, "A"),
    RECOVERING(7, "R");

    private final int length;
    private final String prefix;

    CodeType(int length, String prefix) {
        this.length = length;
        this.prefix = prefix;
    }
}
