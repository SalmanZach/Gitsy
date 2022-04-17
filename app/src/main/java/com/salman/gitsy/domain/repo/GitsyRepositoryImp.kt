package com.salman.gitsy.domain.repo

import com.salman.gitsy.domain.database.dao.UserDao
import com.salman.gitsy.domain.database.entity.UserEntity
import com.salman.gitsy.domain.remote.Envelope
import com.salman.gitsy.domain.remote.GitsyApis
import kotlinx.coroutines.flow.*

/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

class GitsyRepositoryImp(private val remote: GitsyApis, private val userDao: UserDao) :
    GitsyRepository {


    override fun getUserInfo(username: String): Flow<Envelope<UserEntity>> {
        return flow<Envelope<UserEntity>> {
            emit(Envelope.loading())
            // fetch user info from local database
            emit(Envelope.success(userDao.getUserByUsername(username).first()))

            // fetching from server and save it to database
            val apiResponse = remote.getUserInfo(username)
            if (apiResponse.isSuccessful && apiResponse.body() != null) {
                apiResponse.body()?.let {
                    val user = UserEntity(
                        userId = it.id,
                        name = it.name.orEmpty(),
                        username = it.login.orEmpty(),
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
            } else {
                emit(Envelope.error(apiResponse.message()))
            }
            // retrieve latest list
            emitAll(userDao.getUserByUsername(username).map { Envelope.success(it) })
        }.catch { e ->
            emit(Envelope.error(e.message.orEmpty()))
            e.printStackTrace()
        }
    }


    override fun searchUser(query: String): Flow<Envelope<List<UserEntity>>> {
        return flow<Envelope<List<UserEntity>>> {
            emit(Envelope.loading())
            // fetch users from local database
            emit(Envelope.success(userDao.getUserByQuery(query).first()))
            // fetching from server and save it to database
            val apiResponse = remote.searchUser(query)
            if (apiResponse.isSuccessful && apiResponse.body() != null) {
                // clear previous query data
                userDao.clearUsersByQuery(query)

                val response = apiResponse.body()?.userBeans.orEmpty()
                val users = mutableListOf<UserEntity>()
                response.mapTo(users) {
                    UserEntity(
                        userId = it.id,
                        username = it.login.orEmpty(),
                        avatarUrl = it.avatarUrl.orEmpty()
                    )
                }
                userDao.insertUsers(users)
            } else {
                emit(Envelope.error(apiResponse.message()))
            }
            // retrieve latest list
            emitAll(userDao.getUserByQuery(query).map { Envelope.success(it) })
        }.catch { e ->
            emit(Envelope.error(e.message.orEmpty()))
            e.printStackTrace()
        }
    }


}