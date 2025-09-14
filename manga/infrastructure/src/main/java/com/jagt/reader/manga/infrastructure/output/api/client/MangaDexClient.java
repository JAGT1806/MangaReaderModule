package com.jagt.reader.manga.infrastructure.output.api.client;

import com.jagt.reader.manga.infrastructure.output.api.pojo.chapter.ChapterClientResponse;
import com.jagt.reader.manga.infrastructure.output.api.pojo.feed.FeedListClientResponse;
import com.jagt.reader.manga.infrastructure.output.api.pojo.manga.MangaClientResponse;
import com.jagt.reader.manga.infrastructure.output.api.pojo.manga.MangaListClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "manga-service", url = "https://api.mangadex.org")
public interface MangaDexClient {
    @GetMapping("/manga")
    MangaListClientResponse getSearchManga(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam("includes[]") String includes,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "contentRating[]", defaultValue = "safe") List<String> contentRating,
            @RequestParam(value = "availableTranslatedLanguage[]", defaultValue = "es") List<String> language
    );

    @GetMapping("/manga/{id}")
    MangaClientResponse getMangaId(
            @PathVariable String id,
            @RequestParam("includes[]") String include
    );

    @GetMapping("/manga/{id}/feed")
    FeedListClientResponse getMangaIdFeed(
            @PathVariable("id") String id,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "contentRating[]", defaultValue = "safe") List<String> contentRating,
            @RequestParam(value = "includeFutureUpdates", defaultValue = "1") Byte includeFutureUpdates,
            @RequestParam(value = "order[volume]", defaultValue = "asc") String volume,
            @RequestParam(value = "order[chapter]", defaultValue = "asc") String chapter,
            @RequestParam(value = "translatedLanguage[]", defaultValue = "es") List<String> language
    );

    @GetMapping("/at-home/server/{chapterId}")
    ChapterClientResponse getAtHomeServerChapterId(@PathVariable("chapterId") String chapterId);
}
