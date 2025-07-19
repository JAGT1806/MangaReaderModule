package com.jagt.reader.shared.common.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T> {
    private List<T> data;
    private int offset;
    private int limit;
    private long total;
}
