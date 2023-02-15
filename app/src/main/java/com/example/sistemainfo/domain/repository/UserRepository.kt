package com.example.sistemainfo.domain.repository

import com.example.sistemainfo.domain.model.User
import javax.inject.Singleton

@Singleton
interface UserRepository {

    suspend fun getUserFromRoom(id: Int): User?

    suspend fun addUserToRoom(user: User)
}