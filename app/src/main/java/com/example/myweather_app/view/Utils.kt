package com.example.myweather_app.view

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


fun Date.format(): String =
    SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(this);

fun Date.toTime(): String =
    SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(this)

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.showSnackBar (
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}


