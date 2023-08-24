package com.example.di

import com.example.data.Repository
import com.example.domain.TextInteractor
import com.example.domain.TextUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {
    @Provides
    fun interactor(repository: Repository): TextUseCase = TextInteractor(repository)
}