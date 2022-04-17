package com.salman.gitsy.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.gitsy.domain.database.entity.UserEntity
import com.salman.gitsy.domain.remote.Envelope
import com.salman.gitsy.domain.repo.GitsyRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */
class SearchViewModel(private val repository: GitsyRepository) : ViewModel() {

    val users: LiveData<Envelope<List<UserEntity>>>
        get() = _users
    private val _users = MediatorLiveData<Envelope<List<UserEntity>>>()


    val user: LiveData<Envelope<UserEntity>>
        get() = _user
    private val _user = MediatorLiveData<Envelope<UserEntity>>()


    fun query(q: String) {
        viewModelScope.launch {
            repository.searchUser(q).collect {
                _users.postValue(it)
            }
        }
    }


    fun loadUserInfoByUsername(username: String) {
        viewModelScope.launch {
            repository.getUserInfo(username).collect {
                _user.postValue(it)
            }
        }
    }

}