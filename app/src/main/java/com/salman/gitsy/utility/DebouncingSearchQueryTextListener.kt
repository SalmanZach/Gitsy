package com.salman.gitsy.utility

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LifecycleObserver
import kotlinx.coroutines.*

/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

class DebouncingTextListener(
    private val onDebouncingQueryTextChange: (String?) -> Unit
) : TextWatcher, LifecycleObserver {
    private var debouncePeriod: Long = 300

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private var searchJob: Job? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val newText = s.toString().trim()
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newText)
            }
        }

    }
}
