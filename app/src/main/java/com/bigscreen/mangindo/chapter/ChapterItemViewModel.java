package com.bigscreen.mangindo.chapter;


import android.content.res.Resources;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.network.model.Chapter;

public class ChapterItemViewModel {

    private final Resources resources;
    private final Chapter chapter;

    public ChapterItemViewModel(Resources resources, Chapter chapter) {
        this.resources = resources;
        this.chapter = chapter;
    }

    public String getChapterText() {
        if (isChapterNotNull()) {
            if (chapter.getTitle().contains("-")) {
                String[] titleArray = chapter.getTitle().split(" - ");
                if (titleArray.length == 2) {
                   return chapter.getTitle().split(" - ")[0];
                }
                return chapter.getTitle().replace("-", "");
            }
            return getFormattedChapterText(chapter.getHiddenChapter());
        }
        return "N/A";
    }

    public String getChapterTitle() {
        if (isChapterNotNull()) {
            if (chapter.getTitle().contains("-")) {
                String[] titleArray = chapter.getTitle().split(" - ");
                if (titleArray.length == 2) {
                    return chapter.getTitle().split(" - ")[1];
                }
                return "N/A";
            }
            return chapter.getTitle();
        }
        return "N/A";
    }

    private String getFormattedChapterText(String chapterNumber) {
        return String.format(resources.getString(R.string.chapter_), chapterNumber);
    }

    private boolean isChapterNotNull() {
        return chapter != null;
    }
}
