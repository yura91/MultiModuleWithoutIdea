package net.pst.cash.data

interface VerificationRepository {
    suspend fun isVerificationNeeded(token: String): Boolean
    suspend fun verifyUser(token: String): Boolean?
}