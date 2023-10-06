package net.pst.cash.data

import androidx.lifecycle.LiveData
import net.pst.cash.domain.model.CountryModel

interface VerificationRepository {
    val errorMessage: LiveData<String>

    suspend fun isVerificationNeeded(token: String): Boolean

    suspend fun verifyUser(
        token: String,
        firstName: String,
        lastName: String,
        birthDate: String,
        selectedItem: CountryModel?
    ): Boolean?
}