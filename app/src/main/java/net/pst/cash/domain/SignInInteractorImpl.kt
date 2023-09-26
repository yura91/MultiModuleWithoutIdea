package net.pst.cash.domain

import net.pst.cash.data.Repository
import javax.inject.Inject

class SignInInteractorImpl @Inject constructor(private val repository: Repository) :
    SignInInteractor {
    override fun googleSignIn(googleToken: String): String = repository.googleSignIn(googleToken)
}