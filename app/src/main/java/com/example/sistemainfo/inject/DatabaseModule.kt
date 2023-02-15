package com.example.sistemainfo.inject

import android.content.Context
import androidx.room.Room
import com.example.sistemainfo.data.UserDao
import com.example.sistemainfo.data.UserRoomDatabase
import com.example.sistemainfo.feature.commons.Constants.Companion.USER_DB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideUserRoomDatabase(
        context: Context
    ) = Room.databaseBuilder(
        context,
        UserRoomDatabase::class.java,
        USER_DB
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideUserDao(userRoomDatabase: UserRoomDatabase): UserDao {
        return userRoomDatabase.userDao()
    }
}