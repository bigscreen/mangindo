package com.bigscreen.mangindo.common.extension

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showAlert(
        title: String?,
        message: String,
        buttonText: String,
        onClickListener: DialogInterface.OnClickListener
) {
    AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(buttonText, onClickListener)
            .apply { title?.let { setTitle(it) } }
            .show()
}
