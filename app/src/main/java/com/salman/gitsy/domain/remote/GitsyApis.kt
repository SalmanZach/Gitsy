package com.salman.gitsy.domain.remote


import com.salman.gitsy.domain.remote.beans.UserBean
import com.salman.gitsy.domain.remote.beans.UsersResponse
import com.salman.gitsy.utility.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

interface GitsyApis {

    @GET(Constants.METHOD_USER_SEARCH)
    suspend fun searchUser(@Query("q") query: String): Response<UsersResponse>

    @GET(Constants.METHOD_USER_INFO)
    suspend fun getUserInfo(@Path("username") username: String): Response<UserBean>

}