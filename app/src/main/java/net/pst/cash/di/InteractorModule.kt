package net.pst.cash.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pst.cash.data.repos.CountriesRepository
import net.pst.cash.domain.AccountsInteractor
import net.pst.cash.domain.AccountsInteractorImpl
import net.pst.cash.domain.ActiveCardInteractor
import net.pst.cash.domain.ActiveCardInteractorImpl
import net.pst.cash.domain.CardInfoInteractor
import net.pst.cash.domain.CardInfoInteractorImpl
import net.pst.cash.domain.ConfigInteractor
import net.pst.cash.domain.ConfigInteractorImpl
import net.pst.cash.domain.CountriesListInteractor
import net.pst.cash.domain.CountriesListInteractorImpl
import net.pst.cash.domain.HistoryInteractor
import net.pst.cash.domain.HistoryInteractorImpl
import net.pst.cash.domain.SignInInteractor
import net.pst.cash.domain.SignInInteractorImpl
import net.pst.cash.domain.UserInfoIneractorImpl
import net.pst.cash.domain.UserInfoInteractor
import net.pst.cash.domain.VerificationInteractor
import net.pst.cash.domain.VerificationInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface InteractorModule {
    @Binds
    fun signInInteractor(signInInteractor: SignInInteractorImpl): SignInInteractor

    @Binds
    fun verifyInteractor(verifyInteractor: VerificationInteractorImpl): VerificationInteractor

    @Binds
    fun userInfoInteractor(userInfoInteractor: UserInfoIneractorImpl): UserInfoInteractor

    @Binds
    fun activeCardInteractor(cardIsReadyInteractor: ActiveCardInteractorImpl): ActiveCardInteractor

    @Binds
    fun historyListInteractor(transactionListInteractorImpl: HistoryInteractorImpl): HistoryInteractor

    @Binds
    @Singleton
    fun configInteractor(configInteractorImpl: ConfigInteractorImpl): ConfigInteractor

    @Binds
    fun cardInfoInteractor(cardInfoInteractor: CardInfoInteractorImpl): CardInfoInteractor

    @Binds
    @Singleton
    fun accountsInteractor(accountsInteractor: AccountsInteractorImpl): AccountsInteractor

    companion object {
        @Provides
        fun countriesInteractor(countriesRepository: CountriesRepository): CountriesListInteractor =
            CountriesListInteractorImpl(countriesRepository)
    }
}