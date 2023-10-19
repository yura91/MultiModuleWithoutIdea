package net.pst.cash.domain

import net.pst.cash.data.repos.CardInfoRepoImpl
import javax.inject.Inject

class CardInfoInteractorImpl @Inject constructor(private val cardInfoRepo: CardInfoRepoImpl) :
    CardInfoInteractor {
    override suspend fun getCardInfo(token: String, id: String) {
        cardInfoRepo.getCardInfo(token, id)
    }
}