package com.example.sistemainfo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sistemainfo.domain.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM USER_DB WHERE id = :id")
    fun getUser(id: Int): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUserInfo(user: User)
}