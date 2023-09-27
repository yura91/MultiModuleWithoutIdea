package net.pst.cash.domain

import net.pst.cash.data.Repository
import javax.inject.Inject

class SignInInteractorImpl @Inject constructor(private val repository: Repository) :
    SignInInteractor {
    override suspend fun googleSignIn(googleToken: String) = repository.googleSignIn(googleToken)

    override suspend fun signInApple(code: String?) = repository.signInApple(code)

    override suspend fun getAppleLink() = repository.getAppleLink()

}