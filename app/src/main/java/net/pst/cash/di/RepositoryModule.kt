package net.pst.cash.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pst.cash.data.ApiService
import net.pst.cash.data.Repository
import net.pst.cash.data.RepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun repository(api: ApiService): Repository = RepositoryImpl(api)
}