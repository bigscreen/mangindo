package com.bigscreen.mangindo.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        initView();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        String fontName = "fonts/candy.ttf";
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB ||
                android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            setTypeface(typeface);
        }
    }

}
