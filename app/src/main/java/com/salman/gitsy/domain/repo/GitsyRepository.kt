package com.salman.gitsy.domain.repo

import com.salman.gitsy.domain.database.entity.UserEntity
import com.salman.gitsy.domain.remote.Envelope
import kotlinx.coroutines.flow.Flow


/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

interface GitsyRepository {


    fun searchUser(query: String): Flow<Envelope<List<UserEntity>>>

    fun getUserInfo(username: String): Flow<Envelope<UserEntity>>

    // can be added as favourite
    fun addToFavourite(username: String, isFavourite: Boolean)

}