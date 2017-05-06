package com.bigscreen.mangindo.item;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.network.model.Chapter;

public class ChapterViewHolder extends RecyclerView.ViewHolder {

    private TextView textChapter;
    private TextView textChapterTitle;
    private OnChapterClickListener clickListener;

    public ChapterViewHolder(View itemView, OnChapterClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        inflateView();
    }

    private void inflateView() {
        textChapter = (TextView) itemView.findViewById(R.id.text_chapter);
        textChapterTitle = (TextView) itemView.findViewById(R.id.text_chapter_title);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onChapterClick(getAdapterPosition());
                }
            }
        });
    }

    public void bindData(Chapter chapter) {
        String chapterText, chapterTitle;
        if (chapter.getTitle().contains("-")) {
            String[] titleArray = chapter.getTitle().split(" - ");
            if (titleArray.length == 2) {
                chapterText = chapter.getTitle().split(" - ")[0];
                chapterTitle = chapter.getTitle().split(" - ")[1];
            } else {
                chapterText = chapter.getTitle().replace("-", "");
                chapterTitle = "n/a";
            }
        } else {
            chapterText = getFormattedChapterText(chapter.getHiddenChapter());
            chapterTitle = chapter.getTitle();
        }
        textChapter.setText(chapterText);
        textChapterTitle.setText(chapterTitle);
    }

    private String getFormattedChapterText(String chapterNumber) {
        return String.format(itemView.getContext().getString(R.string.chapter_), chapterNumber);
    }

    public interface OnChapterClickListener {
        void onChapterClick(int position);
    }
}
