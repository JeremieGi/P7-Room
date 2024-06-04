package com.openclassrooms.arista.di

import android.content.Context
import com.openclassrooms.arista.dao.AppDatabase
import com.openclassrooms.arista.dao.ExerciseDao
import com.openclassrooms.arista.dao.SleepDao
import com.openclassrooms.arista.dao.UserDao
import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context, coroutineScope: CoroutineScope): AppDatabase {
        return AppDatabase.getDatabase(context, coroutineScope)
    }


    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }


    @Provides
    fun provideSleepDao(appDatabase: AppDatabase): SleepDao {
        return appDatabase.sleepDao()
    }


    @Provides
    fun provideExerciseDao(appDatabase: AppDatabase): ExerciseDao {
        return appDatabase.exerciseDao()
    }


    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }


    @Provides
    @Singleton
    fun provideSleepRepository(sleepDtoDao: SleepDao): SleepRepository {
        return SleepRepository(sleepDtoDao)
    }


    @Provides
    @Singleton
    fun provideExerciseRepository(exerciseDtoDao: ExerciseDao): ExerciseRepository {
        return ExerciseRepository(exerciseDtoDao)
    }
}