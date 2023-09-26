package net.pst.cash.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.pst.cash.data.Repository
import net.pst.cash.domain.SignInInteractor
import net.pst.cash.domain.SignInInteractorImpl

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {
    @Provides
    fun interactor(repository: Repository): SignInInteractor = SignInInteractorImpl(repository)
}