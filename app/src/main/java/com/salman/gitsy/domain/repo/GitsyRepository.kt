package com.salman.gitsy.domain.repo

import com.salman.gitsy.domain.database.entity.UserEntity
import com.salman.gitsy.domain.remote.Envelope
import kotlinx.coroutines.flow.Flow


/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

interface GitsyRepository {

    fun getUserInfo(username: String): Flow<Envelope<UserEntity>>

    fun searchUser(query: String): Flow<Envelope<List<UserEntity>>>


}