package com.jagt.reader.shared.common.domain.model.value;

import java.time.LocalDateTime;

public record AuditTimestampsValue(LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static AuditTimestampsValue now() {
        LocalDateTime now = LocalDateTime.now();
        return new AuditTimestampsValue(now, now);
    }

    public AuditTimestampsValue updated() {
        return new AuditTimestampsValue(this.createdAt, LocalDateTime.now());
    }
}
