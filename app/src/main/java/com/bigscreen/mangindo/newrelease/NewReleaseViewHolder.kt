package com.bigscreen.mangindo.newrelease

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.network.model.Manga
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import java.util.regex.Pattern


class NewReleaseViewHolder(itemView: View, private val context: Context,
                           private val clickListener: OnMangaClickListener) : RecyclerView.ViewHolder(itemView) {

    private lateinit var textTitle: TextView
    private lateinit var textChapter: TextView
    private lateinit var imageCover: ImageView

    init {
        inflateView()
    }

    private fun inflateView() {
        textTitle = itemView.findViewById(R.id.text_title) as TextView
        textChapter = itemView.findViewById(R.id.text_chapter) as TextView
        imageCover = itemView.findViewById(R.id.image_cover) as ImageView
        itemView.setOnClickListener {
            if (adapterPosition != NO_POSITION)
                clickListener.onMangaClick(getAdapterPosition())
        }
    }

    fun bindData(manga: Manga) {
        textTitle.text = manga.title
        textChapter.text = String.format(context.getString(R.string.chapter_), manga.hiddenNewChapter)
        Glide.with(context).load(manga.comicIcon)
                .placeholder(R.drawable.ic_load_image)
                .error(R.drawable.ic_image_error)
                .override(200, 200).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageCover)
    }

    fun bindDataSearch(manga: Manga, keyword: String) {
        bindData(manga)
        textTitle.text = getSearchSpannedTitle(manga.title, keyword)
    }

    private fun getSearchSpannedTitle(title: String, keyword: String): SpannableStringBuilder {
        val spannableStringBuilder = SpannableStringBuilder(title)
        val regex = String.format("(%s)", keyword.toLowerCase())
        val matcher = Pattern.compile(regex).matcher(title.toLowerCase())
        if (matcher.find()) {
            spannableStringBuilder.setSpan(BackgroundColorSpan(Color.YELLOW), matcher.start(1),
                    matcher.end(1), Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        }
        return spannableStringBuilder
    }

    interface OnMangaClickListener {
        fun onMangaClick(position: Int)
    }
}
