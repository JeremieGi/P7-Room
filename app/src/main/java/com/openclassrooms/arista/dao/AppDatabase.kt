package com.openclassrooms.arista.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.entity.UserDto
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

            // 2 utilisateurs de test pour vérifier que les appels au repository renvoie des données filtrées par utilisateur
            val nIDUserTest = userDao.insertUser(
                    UserDto(sName="Jeremie", sEmail = "jg@free.fr", sPassword = "123")
            )
            val nIDUserTest2 = userDao.insertUser(
                UserDto(sName="Geraldine", sEmail = "gg@free.fr", sPassword = "123")
            )

            // 2 sommeils pour l'utilisateur 1
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

            // 2 sommeils pour l'utilisateur 2
            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                    duration = 60,
                    quality = 1,
                    userId = nIDUserTest2
                )
            )

            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(2).atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                    duration = 120,
                    quality = 1,
                    userId = nIDUserTest2
                )
            )


        }
        }

    companion object {
        @Volatile // utilisée en Kotlin pour indiquer qu'une propriété peut être modifiée par plusieurs threads
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

