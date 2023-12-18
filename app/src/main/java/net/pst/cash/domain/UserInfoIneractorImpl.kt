package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.data.repos.UserInfoRepository
import javax.inject.Inject

class UserInfoIneractorImpl @Inject constructor(private val userInfoRepository: UserInfoRepository) :
    UserInfoInteractor {
    override val errorMessage: LiveData<String> = userInfoRepository.errorMessage

    override suspend fun getUserinfo(authToken: String): Boolean =
        userInfoRepository.getUserInfo(authToken)
}