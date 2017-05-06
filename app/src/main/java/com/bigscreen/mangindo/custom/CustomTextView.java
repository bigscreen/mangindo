package com.bigscreen.mangindo.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class CustomTextView extends AppCompatTextView {

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

    private void initView() {
        String fontName = "fonts/candy.ttf";
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
        setTypeface(typeface);
    }

}
