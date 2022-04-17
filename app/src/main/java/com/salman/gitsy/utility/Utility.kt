package com.salman.gitsy.utility

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.salman.gitsy.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

fun Context.formattedTime(date: String): String {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).also {
            it.timeZone = TimeZone.getTimeZone("GMT")
        }
        val givenDate = formatter.parse(date) ?: return ""
        val dateFormat = "MMMM dd, yyyy"
        val newFormat = SimpleDateFormat(dateFormat, Locale.US)
        return newFormat.format(givenDate)
    } catch (e: Exception) {
        ""
    }
}


@BindingAdapter("loadAvatar")
fun AppCompatImageView.bindSrcUrl(url: String?) {
    val request = Glide.with(this).load(url)
        .circleCrop()
        .placeholder(R.drawable.avatar_placeholder)
    request.into(this)
}



