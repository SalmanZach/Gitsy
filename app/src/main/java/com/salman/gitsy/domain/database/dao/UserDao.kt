package com.salman.gitsy.domain.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.salman.gitsy.domain.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE username LIKE '%' || :query || '%'")
    fun getUserByQuery(query: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE username =:username")
    fun getUserByUsername(username: String): Flow<UserEntity>

    @Query("DELETE FROM users WHERE username =:query")
    suspend fun clearUsersByQuery(query: String)

}