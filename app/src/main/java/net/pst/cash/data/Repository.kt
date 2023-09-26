package net.pst.cash.data

interface Repository {
    fun googleSignIn(googleToken: String): String
}