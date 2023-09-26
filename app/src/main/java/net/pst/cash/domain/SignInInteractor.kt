package net.pst.cash.domain

interface SignInInteractor {
    fun googleSignIn(googleToken: String)
}