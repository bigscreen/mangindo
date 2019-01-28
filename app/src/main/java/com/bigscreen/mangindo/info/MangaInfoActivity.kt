package com.bigscreen.mangindo.info

import android.os.Bundle
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.base.BaseActivity
import com.bigscreen.mangindo.common.IntentKey
import com.bigscreen.mangindo.common.extension.setToolbarTitle
import com.bigscreen.mangindo.network.model.Manga
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_manga_info.imageMangaCover
import kotlinx.android.synthetic.main.activity_manga_info.textAuthor
import kotlinx.android.synthetic.main.activity_manga_info.textGenre
import kotlinx.android.synthetic.main.activity_manga_info.textStatus
import kotlinx.android.synthetic.main.activity_manga_info.textSummary

class MangaInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_info)
        setContent()
    }

    private fun setContent() {
        if (intent.hasExtra(IntentKey.MANGA_DATA)) {
            val manga: Manga = intent.getParcelableExtra(IntentKey.MANGA_DATA)
            setToolbarTitle(manga.title, true)
            Glide.with(this).load(manga.comicIcon)
                    .placeholder(R.drawable.ic_load_image)
                    .error(R.drawable.ic_image_error)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageMangaCover)
            with(manga) {
                textAuthor.text = author
                textStatus.text = status
                textGenre.text = genre
                textSummary.text = summary
            }
        } else {
            finish()
        }
    }
}