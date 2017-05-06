package com.bigscreen.mangindo.chapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigscreen.mangindo.databinding.ItemChapterBinding;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class ChapterViewHolder extends RecyclerView.ViewHolder {

    private final ItemChapterBinding binding;
    private final OnChapterClickListener clickListener;

    public ChapterViewHolder(ItemChapterBinding binding, OnChapterClickListener clickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.clickListener = clickListener;
        initViews();
    }

    private void initViews() {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null && getAdapterPosition() != NO_POSITION) {
                    clickListener.onChapterClick(getAdapterPosition());
                }
            }
        });
    }

    public void setViewModel(ChapterItemViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    public interface OnChapterClickListener {
        void onChapterClick(int position);
    }
}
