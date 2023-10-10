package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.data.repos.SignInRepository
import javax.inject.Inject

class SignInInteractorImpl @Inject constructor(private val signInRepository: SignInRepository) :
    SignInInteractor {
    override val errorMessage: LiveData<String> = signInRepository.errorMessage

    override suspend fun signInGoogle(googleToken: String): Boolean =
        signInRepository.signInGoogle(googleToken)

    override suspend fun signInApple(code: String?) = signInRepository.signInApple(code)

    override suspend fun getAppleLink() = signInRepository.getAppleLink()

}