package com.salman.gitsy.utility

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.salman.gitsy.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ln
import kotlin.math.pow


/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

fun Context.formattedTime(date: String?): String {
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


fun Long.formattedCount(): String {
    if (this < 1000) return "" + this
    val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f%c", this / 1000.0.pow(exp.toDouble()), "KMBTPE"[exp - 1])
}


fun View.showWithTranslate() {

    val alpha = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f)
    val translateY = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, height.toFloat(), 0f)
    visibility = View.VISIBLE
    AnimatorSet().apply {
        playTogether(alpha, translateY)
        interpolator = AccelerateInterpolator()
        duration = 500
        start()
    }
}


fun View.hideWithTranslate() {
    val alpha = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f)
    val translateY = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, height.toFloat())
    val set = AnimatorSet()
    set.apply {
        playTogether(alpha, translateY)
        interpolator = AccelerateInterpolator()
        startDelay = 500
        duration = 300
        start()
        doOnEnd { visibility = View.GONE }
    }
}


