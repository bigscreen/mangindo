package com.bigscreen.mangindo.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.network.model.Manga;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MangaViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView textTitle;
    private TextView textChapter;
    private ImageView imageCover;
    private int position;
    private OnMangaClickListener clickListener;

    public MangaViewHolder(View itemView, Context context, OnMangaClickListener clickListener) {
        super(itemView);
        this.context = context;
        this.clickListener = clickListener;
        inflateView(itemView);
    }

    private void inflateView(View itemView) {
        textTitle = (TextView) itemView.findViewById(R.id.text_title);
        textChapter = (TextView) itemView.findViewById(R.id.text_chapter);
        imageCover = (ImageView) itemView.findViewById(R.id.image_cover);
        initClicker(itemView);
    }

    private void initClicker(View itemView) {
        View viewClicker = itemView.findViewById(R.id.view_clicker);
        viewClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onMangaClick(position);
            }
        });
    }

    public void bindData(Manga manga, int position) {
        this.position = position;
        textTitle.setText(manga.getJudul());
        textChapter.setText(String.format(context.getString(R.string.chapter_), manga.getHiddenNewChapter()));
        Glide.with(context).load(manga.getIconKomik())
                .placeholder(R.drawable.ic_load_image)
                .error(R.drawable.ic_image_error)
                .override(200, 200).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageCover);
    }

    public interface OnMangaClickListener {
        void onMangaClick(int position);
    }
}
