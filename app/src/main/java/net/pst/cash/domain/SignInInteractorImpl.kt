package net.pst.cash.domain

import net.pst.cash.data.Repository
import javax.inject.Inject

class SignInInteractorImpl @Inject constructor(private val repository: Repository) :
    SignInInteractor {
    override suspend fun signInGoogle(googleToken: String) = repository.signInGoogle(googleToken)

    override suspend fun signInApple(code: String?) = repository.signInApple(code)

    override suspend fun getAppleLink() = repository.getAppleLink()

}