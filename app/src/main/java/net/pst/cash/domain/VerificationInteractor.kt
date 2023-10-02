package net.pst.cash.domain

import net.pst.cash.domain.model.CountryModel

interface VerificationInteractor {
    suspend fun isVerificationNeeded(token: String): Boolean
    suspend fun verifyUser(
        token: String,
        firstName: String,
        lastName: String,
        birthDate: String,
        selectedItem: CountryModel?
    ): Boolean?
}