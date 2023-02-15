package com.example.sistemainfo.inject

import com.example.sistemainfo.data.UserRepositoryImpl
import com.example.sistemainfo.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserRepository(repository: UserRepositoryImpl): UserRepository
}