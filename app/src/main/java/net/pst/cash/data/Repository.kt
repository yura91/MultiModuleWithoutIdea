package net.pst.cash.data

interface Repository {
    suspend fun googleSignIn(googleToken: String)
}