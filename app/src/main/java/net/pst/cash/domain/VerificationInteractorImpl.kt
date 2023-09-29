package net.pst.cash.domain

import net.pst.cash.data.VerificationRepository
import javax.inject.Inject

class VerificationInteractorImpl @Inject constructor(private val verificationRepository: VerificationRepository) :
    VerificationInteractor {
    override suspend fun isVerificationNeeded(token: String): Boolean =
        verificationRepository.isVerificationNeeded(token)

    override suspend fun verifyUser(token: String): Boolean? =
        verificationRepository.verifyUser(token)
}