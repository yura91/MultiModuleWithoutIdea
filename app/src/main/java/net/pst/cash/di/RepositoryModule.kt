package net.pst.cash.di

import android.content.Context
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
    fun repository(api: ApiService, context: Context): Repository = RepositoryImpl(api, context)
}