package com.openclassrooms.arista.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.arista.data.ExerciseDto
import com.openclassrooms.arista.data.SleepDto
import com.openclassrooms.arista.data.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

@Database(entities = [UserDto::class, SleepDto::class, ExerciseDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun sleepDao(): SleepDao
    abstract fun exerciseDao(): ExerciseDao

    private class AppDatabaseCallback(

        private val scope: CoroutineScope

    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    initDatabase(database.userDao(), database.sleepDao())
                }
            }
        }

        suspend fun initDatabase(userDao : UserDao, sleepDao: SleepDao) {

            // Utilisateur de test
            val nIDUserTest = userDao.insertUser(
                    UserDto(sName="Jeremie", sEmail = "jg@free.fr", sPassword = "123")
            )

            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                    duration = 480,
                    quality = 4,
                    userId = nIDUserTest
                )
            )

            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(2).atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                    duration = 450,
                    quality = 3,
                    userId = nIDUserTest
                )
            )

        }
        }

    companion object {
        @Volatile // TODO : voir cette annotation
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AristaDB"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}