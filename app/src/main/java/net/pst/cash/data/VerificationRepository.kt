package net.pst.cash.data

interface VerificationRepository {
    suspend fun isVerificationNeeded(token: String): Boolean
}