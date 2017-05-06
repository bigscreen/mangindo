package com.bigscreen.mangindo.content;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.databinding.FragmentMangaContentBinding;
import com.bigscreen.mangindo.listener.OnContentImageClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MangaContentFragment extends Fragment implements PhotoViewAttacher.OnViewTapListener {

    public static final String KEY_IMAGE_URL = "IMAGE_URL";

    private FragmentMangaContentBinding binding;

    private int fragmentPosition;

    public static MangaContentFragment getInstance(String imageUrl) {
        MangaContentFragment fragment = new MangaContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMAGE_URL, imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_manga_content,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imageManga.setOnViewTapListener(this);
        String imageUrl = getArguments().getString(KEY_IMAGE_URL);
        loadImage(imageUrl);
    }

    private void loadImage(String imageUrl) {
        Glide.with(getContext()).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        binding.progressLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        binding.progressLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(binding.imageManga);
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        if (view.getId() == R.id.image_manga) {
            OnContentImageClickListener listener = (OnContentImageClickListener) getActivity();
            if (listener != null) {
                listener.OnContentImageClick(fragmentPosition);
            }
        }
    }

    public void setFragmentPosition(int fragmentPosition) {
        this.fragmentPosition = fragmentPosition;
    }

}
