package com.example.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [ApiModule::class, RepositoryModule::class, InteractorModule::class]
)
@InstallIn(SingletonComponent::class)

object DataAggregatorModule {
}