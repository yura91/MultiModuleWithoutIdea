package net.pst.cash.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(
    private val api: ApiService
) : VerificationRepository {
    override suspend fun isVerificationNeeded(token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val verActualResponse = api.isVerificationNeeded(token)
                if (verActualResponse.isSuccessful) {
                    val step = verActualResponse.body()?.data?.step
                    true
//                    step.isNullOrEmpty()
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    override suspend fun verifyUser(token: String): Boolean? {
        return withContext(Dispatchers.IO) {
            try {
                val verifyResponse = api.verifyUser(token, VerificationRequest())
                if (verifyResponse.isSuccessful) {
                    verifyResponse.body()?.success
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }


}