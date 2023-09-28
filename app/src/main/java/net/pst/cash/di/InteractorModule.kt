package net.pst.cash.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pst.cash.data.CountriesRepository
import net.pst.cash.domain.CountriesListInteractor
import net.pst.cash.domain.CountriesListInteractorImpl
import net.pst.cash.domain.SignInInteractor
import net.pst.cash.domain.SignInInteractorImpl

@Module
@InstallIn(SingletonComponent::class)
interface InteractorModule {
    @Binds
    fun signInInteractor(signInInteractor: SignInInteractorImpl): SignInInteractor

    companion object {
        @Provides
        fun countriesInteractor(countriesRepository: CountriesRepository): CountriesListInteractor =
            CountriesListInteractorImpl(countriesRepository)
    }
}