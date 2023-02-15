package com.example.sistemainfo.data

import com.example.sistemainfo.domain.model.User
import com.example.sistemainfo.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserFromRoom(id: Int) = userDao.getUser(id)

    override suspend fun addUserToRoom(user: User) = userDao.addUserInfo(user)
}