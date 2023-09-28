package net.pst.cash.domain

import net.pst.cash.data.SignInRepository
import javax.inject.Inject

class SignInInteractorImpl @Inject constructor(private val signInRepository: SignInRepository) :
    SignInInteractor {
    override suspend fun signInGoogle(googleToken: String): Boolean =
        signInRepository.signInGoogle(googleToken)

    override suspend fun signInApple(code: String?) = signInRepository.signInApple(code)

    override suspend fun getAppleLink() = signInRepository.getAppleLink()

}