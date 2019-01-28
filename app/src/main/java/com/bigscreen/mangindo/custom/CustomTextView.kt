package com.bigscreen.mangindo.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setupView()
    }

    private fun setupView() {
        typeface = Typeface.createFromAsset(context.assets, "fonts/candy.ttf")
    }
}
