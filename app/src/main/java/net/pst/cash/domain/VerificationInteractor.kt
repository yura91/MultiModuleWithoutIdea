package net.pst.cash.domain

interface VerificationInteractor {
    suspend fun isVerificationNeeded(token: String): Boolean
    suspend fun verifyUser(token: String): Boolean?
}