package com.codeex.task.base.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*


/**
 *  Converting String date depending [fromPattern] and [toPattern]
 */
@SuppressLint("SimpleDateFormat")
fun String.convertDatePattern(fromPattern: String, toPattern: String): String {
    return try {
        val df = SimpleDateFormat(fromPattern)
        val parsedDate = df.parse(this)
        val timestamp = parsedDate?.time ?: return ""
        val date = Date(timestamp)
        val format = SimpleDateFormat(toPattern)
        format.format(date)
    } catch (error: Exception) {
        ""
    }
}

fun Activity.toast(messageId: Int) {
    Toast.makeText(this, getString(messageId), Toast.LENGTH_LONG).show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
}