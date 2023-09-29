package net.pst.cash.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pst.cash.data.CountriesRepository
import net.pst.cash.data.CountriesRepositoryImpl
import net.pst.cash.data.SignInRepository
import net.pst.cash.data.SignInRepositoryImpl
import net.pst.cash.data.VerificationRepository
import net.pst.cash.data.VerificationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindSingInRepository(signInRepositoryImpl: SignInRepositoryImpl): SignInRepository

    @Binds
    fun bindCountriesRepository(countriesRepository: CountriesRepositoryImpl): CountriesRepository

    @Binds
    fun bindVerificationRepository(verificationRepository: VerificationRepositoryImpl): VerificationRepository
}