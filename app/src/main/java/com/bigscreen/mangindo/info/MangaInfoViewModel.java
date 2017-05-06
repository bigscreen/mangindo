package com.bigscreen.mangindo.info;


import com.bigscreen.mangindo.network.model.Manga;

public class MangaInfoViewModel {

    private final Manga manga;

    public MangaInfoViewModel(Manga manga) {
        this.manga = manga;
    }

    public String getAuthorName() {
        return isMangaNotNull()? manga.getAuthor() : "N/A";
    }

    public String getStatus() {
        return isMangaNotNull()? manga.getStatus() : "N/A";
    }

    public String getGenre() {
        return isMangaNotNull()? manga.getGenre() : "N/A";
    }

    public String getSummary() {
        return isMangaNotNull()? manga.getSummary() : "N/A";
    }

    private boolean isMangaNotNull() {
        return manga != null;
    }
}
