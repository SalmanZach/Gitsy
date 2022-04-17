package com.salman.gitsy.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salman.gitsy.domain.repo.GitsyRepository
import com.salman.gitsy.view.search.SearchViewModel

/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

class ViewModelFactory(private val repository: GitsyRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(SearchViewModel::class.java) ->
                    SearchViewModel(repository)
                else ->
                    error("Invalid View Model class from appViewModelFactory")
            }
        } as T
}