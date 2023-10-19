package net.pst.cash.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pst.cash.data.repos.CardInfoRepo
import net.pst.cash.data.repos.CardInfoRepoImpl
import net.pst.cash.data.repos.CardIsReadyRepo
import net.pst.cash.data.repos.CardIsReadyRepoImpl
import net.pst.cash.data.repos.CountriesRepository
import net.pst.cash.data.repos.CountriesRepositoryImpl
import net.pst.cash.data.repos.SignInRepository
import net.pst.cash.data.repos.SignInRepositoryImpl
import net.pst.cash.data.repos.UserInfoRepository
import net.pst.cash.data.repos.UserInfoRepositoryimpl
import net.pst.cash.data.repos.VerificationRepository
import net.pst.cash.data.repos.VerificationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindSingInRepository(signInRepositoryImpl: SignInRepositoryImpl): SignInRepository

    @Binds
    fun bindCardInfoRepository(cardInfoRepositoryImpl: CardInfoRepoImpl): CardInfoRepo

    @Binds
    fun bindCountriesRepository(countriesRepository: CountriesRepositoryImpl): CountriesRepository

    @Binds
    fun bindCardIsReadyRepository(cardIsReadyRepository: CardIsReadyRepoImpl): CardIsReadyRepo

    @Binds
    fun bindVerificationRepository(verificationRepository: VerificationRepositoryImpl): VerificationRepository

    @Binds
    fun bindUserInfoRepository(userInfoRepository: UserInfoRepositoryimpl): UserInfoRepository
}