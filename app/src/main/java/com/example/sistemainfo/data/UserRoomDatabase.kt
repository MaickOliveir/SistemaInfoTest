package com.example.sistemainfo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sistemainfo.domain.model.User


@Database(entities = [(User::class)], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}