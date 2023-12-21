package net.pst.cash.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pst.cash.data.repos.AccountsRepo
import net.pst.cash.data.repos.AccountsRepoImpl
import net.pst.cash.data.repos.ActiveCardsRepo
import net.pst.cash.data.repos.ActiveCardsRepoImpl
import net.pst.cash.data.repos.CardInfoRepo
import net.pst.cash.data.repos.CardInfoRepoImpl
import net.pst.cash.data.repos.ConfigRepo
import net.pst.cash.data.repos.ConfigRepoImpl
import net.pst.cash.data.repos.CountriesRepository
import net.pst.cash.data.repos.CountriesRepositoryImpl
import net.pst.cash.data.repos.HistoryListRepo
import net.pst.cash.data.repos.HistoryListRepoImpl
import net.pst.cash.data.repos.IssueCardRepo
import net.pst.cash.data.repos.IssueCardRepoImpl
import net.pst.cash.data.repos.SignInRepository
import net.pst.cash.data.repos.SignInRepositoryImpl
import net.pst.cash.data.repos.UserInfoRepository
import net.pst.cash.data.repos.UserInfoRepositoryImpl
import net.pst.cash.data.repos.VerificationRepository
import net.pst.cash.data.repos.VerificationRepositoryImpl
import javax.inject.Singleton

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
    fun bindActiveCardsRepository(activeCardsRepository: ActiveCardsRepoImpl): ActiveCardsRepo

    @Binds
    fun bindVerificationRepository(verificationRepository: VerificationRepositoryImpl): VerificationRepository

    @Binds
    fun bindUserInfoRepository(userInfoRepository: UserInfoRepositoryImpl): UserInfoRepository

    @Binds
    fun bindHistoryListRepository(transactionsListRepo: HistoryListRepoImpl): HistoryListRepo

    @Binds
    fun bindAccountsRepository(accountsRepo: AccountsRepoImpl): AccountsRepo

    @Binds
    fun bindIssueCardRepository(issueCardRepo: IssueCardRepoImpl): IssueCardRepo

    @Binds
    @Singleton
    fun bindConfigRepository(configRepository: ConfigRepoImpl): ConfigRepo
}