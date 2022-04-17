package com.salman.gitsy.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salman.gitsy.domain.database.dao.UserDao
import com.salman.gitsy.domain.database.entity.UserEntity

/**
 * Created by Salman Saifi on 18/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)

abstract class GitsyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: GitsyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                GitsyDatabase::class.java, "gitsy_app.db"
            ).build()
    }

}