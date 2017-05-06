package com.bigscreen.mangindo.manga.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.listener.OnContentImageClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MangaContentFragment extends Fragment implements PhotoViewAttacher.OnViewTapListener {

    public static final String KEY_IMAGE_URL = "IMAGE_URL";

    private PhotoView imageManga;

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
        View view = inflater.inflate(R.layout.fragment_manga_image, container, false);
        imageManga = (PhotoView) view.findViewById(R.id.image_manga);
        imageManga.setOnViewTapListener(this);
        String imageUrl = getArguments().getString(KEY_IMAGE_URL);
        loadImage(imageUrl);

        return view;
    }

    private void loadImage(String imageUrl) {
        Glide.with(getContext()).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageManga);
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
