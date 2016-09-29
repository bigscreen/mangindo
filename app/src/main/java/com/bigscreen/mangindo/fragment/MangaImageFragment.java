package com.bigscreen.mangindo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.common.Constant;
import com.bigscreen.mangindo.common.Utils;
import com.bigscreen.mangindo.listener.OnContentImageClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MangaImageFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_IMAGE_URL = "IMAGE_URL";

    private ImageView imageManga;

    private int fragmentPosition;

    public static MangaImageFragment getInstance(String imageUrl) {
        MangaImageFragment fragment = new MangaImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMAGE_URL, imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_image, container, false);
        imageManga = (ImageView) view.findViewById(R.id.image_manga);
        imageManga.setOnClickListener(this);

        String imageUrl = getArguments().getString(KEY_IMAGE_URL);
        loadImage(imageUrl);

        return view;
    }

    private void loadImage(String imageUrl) {
        Log.d(Constant.LOG_TAG, "imageUrl= " + imageUrl);
        Log.d(Constant.LOG_TAG, "encodedImageUrl= " + Utils.getEncodedUrl(imageUrl));
        Glide.with(getContext()).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageManga);
    }

    @Override
    public void onClick(View view) {
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
