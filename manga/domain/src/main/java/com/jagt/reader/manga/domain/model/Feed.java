package com.jagt.reader.manga.domain.model;

import lombok.Data;

@Data
public class Feed {
    private String id;
    private String volume;
    private String chapter;
    private String title;
    private String translatedLanguage;
    private int pages;
}
