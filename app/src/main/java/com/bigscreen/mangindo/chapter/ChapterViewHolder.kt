package com.bigscreen.mangindo.chapter

import android.support.v7.widget.RecyclerView
import com.bigscreen.mangindo.databinding.ItemChapterBinding


class ChapterViewHolder(val binding: ItemChapterBinding, val clickListener: OnChapterClickListener): RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                clickListener.onChapterClick(adapterPosition)
            }
        }
    }

    fun setViewModel(viewModel: ChapterItemViewModel) {
        binding.viewModel = viewModel
    }

    interface OnChapterClickListener {
        fun onChapterClick(position: Int)
    }
}