package com.example.di

import com.example.data.ApiService
import com.example.data.Repository
import com.example.data.RepositoryImpl
import com.example.domain.TextInteractor
import com.example.domain.TextUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MainAppModule {

    @Provides
    fun apiService(): ApiService {
        val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun repository(): Repository = RepositoryImpl()

    @Provides
    fun interactor(repository: Repository): TextUseCase = TextInteractor(repository)
}