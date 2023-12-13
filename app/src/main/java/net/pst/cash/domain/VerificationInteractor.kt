package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.domain.model.CountryModel

interface VerificationInteractor {
    val errorMessage: LiveData<String>

    suspend fun isVerificationNeeded(token: String): Boolean?

    suspend fun verifyUser(
        token: String,
        firstName: String,
        lastName: String,
        birthDate: String,
        selectedItem: CountryModel?
    ): Boolean?
}