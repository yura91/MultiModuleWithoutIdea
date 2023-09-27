package net.pst.cash.domain

interface SignInInteractor {
    suspend fun googleSignIn(googleToken: String)
}