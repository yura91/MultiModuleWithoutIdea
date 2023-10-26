package net.pst.cash.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pst.cash.data.repos.CountriesRepository
import net.pst.cash.domain.CardInfoInteractor
import net.pst.cash.domain.CardInfoInteractorImpl
import net.pst.cash.domain.CardIsReadyInteractor
import net.pst.cash.domain.CardIsReadyInteractorImpl
import net.pst.cash.domain.CountriesListInteractor
import net.pst.cash.domain.CountriesListInteractorImpl
import net.pst.cash.domain.HistoryInteractor
import net.pst.cash.domain.HistoryInteractorImpl
import net.pst.cash.domain.SignInInteractor
import net.pst.cash.domain.SignInInteractorImpl
import net.pst.cash.domain.UserInfoIneractorimpl
import net.pst.cash.domain.UserInfoInteractor
import net.pst.cash.domain.VerificationInteractor
import net.pst.cash.domain.VerificationInteractorImpl

@Module
@InstallIn(SingletonComponent::class)
interface InteractorModule {
    @Binds
    fun signInInteractor(signInInteractor: SignInInteractorImpl): SignInInteractor

    @Binds
    fun verifyInteractor(verifyInteractor: VerificationInteractorImpl): VerificationInteractor

    @Binds
    fun userInfoInteractor(userInfoInteractor: UserInfoIneractorimpl): UserInfoInteractor

    @Binds
    fun cardIsReadyInteractor(cardIsReadyInteractor: CardIsReadyInteractorImpl): CardIsReadyInteractor

    @Binds
    fun historyListInteractor(transactionListInteractorImpl: HistoryInteractorImpl): HistoryInteractor

    @Binds
    fun cardInfoInteractor(cardInfoInteractor: CardInfoInteractorImpl): CardInfoInteractor

    companion object {
        @Provides
        fun countriesInteractor(countriesRepository: CountriesRepository): CountriesListInteractor =
            CountriesListInteractorImpl(countriesRepository)
    }
}