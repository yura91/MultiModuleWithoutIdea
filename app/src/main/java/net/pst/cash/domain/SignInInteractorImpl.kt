package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.data.repos.SignInRepository
import javax.inject.Inject

class SignInInteractorImpl @Inject constructor(private val signInRepository: SignInRepository) :
    SignInInteractor {
    override val errorMessage: LiveData<String> = signInRepository.errorMessage

    override suspend fun signInGoogle(registerHash: String, googleToken: String): Boolean =
        signInRepository.signInGoogle(registerHash, googleToken)

    override suspend fun signInApple(code: String?) = signInRepository.signInApple(code)

    override suspend fun getAppleLink() = signInRepository.getAppleLink()

}