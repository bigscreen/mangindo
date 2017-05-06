package com.bigscreen.mangindo.manga.info;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.base.BaseActivity;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.common.IntentKey;
import com.bigscreen.mangindo.databinding.ActivityMangaInfoBinding;
import com.bigscreen.mangindo.network.model.Manga;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MangaInfoActivity extends BaseActivity {

    private ActivityMangaInfoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manga_info);
        setContent();
    }

    private void setContent() {
        Manga manga = getIntent().getExtras().getParcelable(IntentKey.MANGA_DATA);
        if (manga == null) {
            Log.e(Constant.LOG_TAG, "Could not found intent extra");
            finish();
            return;
        }
        binding.setViewModel(new MangaInfoViewModel(manga));
        setToolbarTitle(manga.getTitle(), true);
        Glide.with(this).load(manga.getComicIcon())
                .placeholder(R.drawable.ic_load_image)
                .error(R.drawable.ic_image_error)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(binding.imageMangaCover);
    }
}
