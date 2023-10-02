package net.pst.cash.data

import net.pst.cash.domain.model.CountryModel

interface VerificationRepository {
    suspend fun isVerificationNeeded(token: String): Boolean
    suspend fun verifyUser(
        token: String,
        firstName: String,
        lastName: String,
        birthDate: String,
        selectedItem: CountryModel?
    ): Boolean?
}