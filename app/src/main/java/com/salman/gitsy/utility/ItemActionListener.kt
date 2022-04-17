package com.salman.gitsy.utility

import android.view.View


/**
 * Created by Salman Saifi on 17/04/22.
 * Email - zach.salmansaifi@gmail.com
 */

interface ItemActionListener<T> {
    fun onItemClicked(view: View, model: T)
}