package com.salman.gitsy.domain.repo

import com.salman.gitsy.domain.database.dao.UserDao
import com.salman.gitsy.domain.database.entity.UserEntity
import com.salman.gitsy.domain.remote.Envelope
import com.salman.gitsy.domain.remote.GitsyApis
import com.salman.gitsy.domain.remote.NetworkBoundResponse
import com.salman.gitsy.domain.remote.beans.UserBean
import com.salman.gitsy.domain.remote.beans.UsersResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import retrofit2.Response

/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

class GitsyRepositoryImp(private val remote: GitsyApis, private val userDao: UserDao) :
    GitsyRepository {


    override fun searchUser(query: String): Flow<Envelope<List<UserEntity>>> {

        return object : NetworkBoundResponse<List<UserEntity>, UsersResponse>() {

            override suspend fun saveRemoteData(response: UsersResponse) {
                val data = response.userBeans.orEmpty()
                val users = mutableListOf<UserEntity>()
                data.mapTo(users) {
                    UserEntity(
                        userId = it.id,
                        userName = it.login.orEmpty(),
                        avatarUrl = it.avatarUrl.orEmpty()
                    )
                }
                userDao.clearUsers()
                userDao.insertUsers(users)
            }

            override fun fetchFromLocal(): Flow<List<UserEntity>> =
                userDao.getUserByQuery(query).distinctUntilChanged()

            override suspend fun fetchFromRemote(): Response<UsersResponse> =
                remote.searchUser(query)
        }.asFlow()

    }


    override fun getUserInfo(username: String): Flow<Envelope<UserEntity>> {

        return object : NetworkBoundResponse<UserEntity, UserBean>() {

            override suspend fun saveRemoteData(response: UserBean) {
                response.let {
                    val user = UserEntity(
                        userId = it.id,
                        name = it.name.orEmpty(),
                        userName = it.login.orEmpty(),
                        avatarUrl = it.avatarUrl.orEmpty(),
                        bio = it.bio.orEmpty(),
                        company = it.company.orEmpty(),
                        createdAt = it.createdAt.orEmpty(),
                        email = it.email.orEmpty(),
                        followers = it.followers ?: 0,
                        following = it.following ?: 0,
                        hireable = it.hireable ?: false,
                        publicGists = it.publicGists ?: 0,
                        publicRepos = it.publicRepos ?: 0,
                        twitterUsername = it.twitterUsername.orEmpty()
                    )
                    userDao.insertUser(user)
                }
            }

            override fun fetchFromLocal(): Flow<UserEntity> =
                userDao.getUserByUsername(username).distinctUntilChanged()

            override suspend fun fetchFromRemote(): Response<UserBean> =
                remote.getUserInfo(username)
        }.asFlow()
    }


    override fun addToFavourite(username: String, isFavourite: Boolean) {
        TODO("Can be added as favourite for a user by username")
    }


}