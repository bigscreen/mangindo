package com.bigscreen.mangindo.item;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.network.model.Chapter;

public class ChapterItemView extends LinearLayout {

    private TextView textChapter;
    private TextView textChapterTitle;

    public ChapterItemView(Context context) {
        super(context);
        inflateView();
    }

    private void inflateView() {
        inflate(getContext(), R.layout.item_chapter, this);
        textChapter = (TextView) findViewById(R.id.text_chapter);
        textChapterTitle = (TextView) findViewById(R.id.text_chapter_title);
    }

    public void bindData(Chapter chapter) {
        String chapterText, chapterTitle;
        if (chapter.getTitle().contains("-")) {
            chapterText = chapter.getTitle().split("-")[0];
            chapterTitle = chapter.getTitle().split("-")[1];
            if (chapterText.isEmpty())
                chapterText = String.format(getContext().getString(R.string.chapter_), chapter.getHiddenChapter());
            if (chapterTitle.isEmpty())
                chapterTitle = chapter.getTitle();
        } else {
            chapterText = String.format(getContext().getString(R.string.chapter_), chapter.getHiddenChapter());
            chapterTitle = chapter.getTitle();
        }
        textChapter.setText(chapterText);
        textChapterTitle.setText(chapterTitle);
    }

}
