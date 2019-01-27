package com.bigscreen.mangindo.chapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_chapter.view.textChapter
import kotlinx.android.synthetic.main.item_chapter.view.textChapterTitle

class ChapterViewHolder(itemView: View,
                        private val clickListener: OnChapterClickListener)
    : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                clickListener.onChapterClick(adapterPosition)
            }
        }
    }

    fun setViewModel(viewModel: ChapterItemViewModel) {
        itemView.textChapter.text = viewModel.chapterText
        itemView.textChapterTitle.text = viewModel.chapterTitle
    }

    interface OnChapterClickListener {
        fun onChapterClick(position: Int)
    }
}