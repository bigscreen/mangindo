package com.bigscreen.mangindo.info

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.bigscreen.mangindo.R
import com.bigscreen.mangindo.base.BaseActivity
import com.bigscreen.mangindo.common.IntentKey
import com.bigscreen.mangindo.databinding.ActivityMangaInfoBinding
import com.bigscreen.mangindo.network.model.Manga
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class MangaInfoActivity : BaseActivity() {

    private lateinit var binding: ActivityMangaInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manga_info)
        setContent()
    }

    private fun setContent() {
        if (intent.hasExtra(IntentKey.MANGA_DATA)) {
            val manga: Manga = intent.getParcelableExtra(IntentKey.MANGA_DATA)
            binding.viewModel = MangaInfoViewModel(manga)
            setToolbarTitle(manga.title, true)
            Glide.with(this).load(manga.comicIcon)
                    .placeholder(R.drawable.ic_load_image)
                    .error(R.drawable.ic_image_error)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(binding.imageMangaCover)
        } else {
            finish()
        }
    }
}