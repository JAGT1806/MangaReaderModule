package com.jagt.reader.manga.application.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class LanguageUtils {
    private static final Set<String> SUPPORTED_LANGUAGES = Set.of("es", "en", "fr");

    private LanguageUtils() {}

    public static List<String> getAvailableLanguages(String language) {
        return language.equals("es") ? List.of("es", "es-la") : List.of(language);
    }

    public static String getHeaderLanguage() {
        Locale locale = LocaleContextHolder.getLocale();
        String language = locale.getLanguage();

        return SUPPORTED_LANGUAGES.contains(language) ? language : "es";
    }

    public static List<String> getAvailableTranslatedLanguages() {
        return getAvailableLanguages(getHeaderLanguage());
    }
}
