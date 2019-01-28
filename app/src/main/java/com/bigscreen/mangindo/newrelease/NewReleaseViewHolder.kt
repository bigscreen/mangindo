package com.bigscreen.mangindo.newrelease

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.network.model.Manga
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_new_release.view.imageCover
import kotlinx.android.synthetic.main.item_new_release.view.textChapter
import kotlinx.android.synthetic.main.item_new_release.view.textTitle
import java.util.regex.Pattern

class NewReleaseViewHolder(
        itemView: View,
        private val context: Context,
        private val clickListener: OnMangaClickListener
) : RecyclerView.ViewHolder(itemView) {

    init {
        inflateView()
    }

    private fun inflateView() {
        itemView.setOnClickListener {
            if (adapterPosition != NO_POSITION) clickListener.onMangaClick(adapterPosition)
        }
    }

    fun bindData(manga: Manga) {
        itemView.textTitle.text = manga.title
        itemView.textChapter.text = String.format(
                context.getString(R.string.chapter_),
                manga.hiddenNewChapter
        )
        Glide.with(context)
                .load(manga.comicIcon)
                .placeholder(R.drawable.ic_load_image)
                .error(R.drawable.ic_image_error)
                .override(200, 200).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(itemView.imageCover)
    }

    fun bindDataSearch(manga: Manga, keyword: String) {
        bindData(manga)
        itemView.textTitle.text = getSearchSpannedTitle(manga.title, keyword)
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
