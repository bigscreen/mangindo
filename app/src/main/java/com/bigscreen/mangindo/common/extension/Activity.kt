package com.bigscreen.mangindo.common.extension

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bigscreen.mangindo.R

fun Activity.setLightStatusBar(light: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val flags = if (light) {
            window.statusBarColor = Color.WHITE
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        window.decorView.systemUiVisibility = flags
    } else {
        window.decorView.systemUiVisibility = 0
    }
}

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Activity.showKeyboard(editText: EditText) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.showSoftInput(editText, 0)
}

fun AppCompatActivity.setToolbarTitle(title: String, showBackButton: Boolean) {
    supportActionBar?.let {
        it.title = title
        it.setHomeButtonEnabled(showBackButton)
        it.setDisplayHomeAsUpEnabled(showBackButton)
    }
}

fun AppCompatActivity.setToolbarSubtitle(subtitle: String) {
    supportActionBar?.setSubtitle(subtitle)
}

