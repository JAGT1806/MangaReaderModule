package com.jagt.reader.manga.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manga {
    private String id;
    private String title;
    private String description;
    private String coverId;
    private String fileName;
}
