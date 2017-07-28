package com.bigscreen.mangindo.chapter

import android.content.res.Resources
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.network.model.Chapter


class ChapterItemViewModel(val resources: Resources, val chapter: Chapter?) {

    var chapterText: String = "N/A"
        get() {
            chapter?.let {
                if (it.title.contains("-")) {
                    val titleArray = it.title.split(" - ")
                    if (titleArray.size == 2) {
                        return it.title.split(" - ")[0]
                    }
                    return chapter.title.replace("-", "", true)
                }
                return getFormattedChapterText(it.hiddenChapter)
            }
            return "N/A"
        }

    var chapterTitle: String = "N/A"
        get() {
            chapter?.let {
                if (it.title.contains("-")) {
                    val titleArray = it.title.split(" - ")
                    if (titleArray.size == 2) {
                        return it.title.split(" - ")[1]
                    }
                    return "N/A"
                }
                return it.title
            }
            return "N/A";
        }

    private fun getFormattedChapterText(chapterNumber: String): String
            = String.format(resources.getString(R.string.chapter_), chapterNumber)

}