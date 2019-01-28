package com.bigscreen.mangindo.common.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class TopCropImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        scaleType = ScaleType.MATRIX
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        if (drawable != null) {
            val matrix = imageMatrix
            val scaleFactor = width / drawable.intrinsicWidth.toFloat()
            matrix.setScale(scaleFactor, scaleFactor, 0f, 0f)
            imageMatrix = matrix
        }
        return super.setFrame(l, t, r, b)
    }
}
