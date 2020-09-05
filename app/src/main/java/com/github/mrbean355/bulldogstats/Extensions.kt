package com.github.mrbean355.bulldogstats

import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Snackbar.showSuccess() {
    view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.success))
    show()
}

fun Snackbar.showError() {
    view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.error))
    show()
}