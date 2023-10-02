package net.pst.cash.domain

import net.pst.cash.data.VerificationRepository
import net.pst.cash.domain.model.CountryModel
import javax.inject.Inject

class VerificationInteractorImpl @Inject constructor(private val verificationRepository: VerificationRepository) :
    VerificationInteractor {
    override suspend fun isVerificationNeeded(token: String): Boolean =
        verificationRepository.isVerificationNeeded(token)

    override suspend fun verifyUser(
        token: String,
        firstName: String,
        lastName: String,
        birthDate: String,
        selectedItem: CountryModel?
    ): Boolean? =
        verificationRepository.verifyUser(token, firstName, lastName, birthDate, selectedItem)
}