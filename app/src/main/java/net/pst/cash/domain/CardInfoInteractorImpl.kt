package net.pst.cash.domain

import net.pst.cash.data.repos.CardInfoRepoImpl
import net.pst.cash.domain.model.ShowPanDataModel
import javax.inject.Inject

class CardInfoInteractorImpl @Inject constructor(private val cardInfoRepo: CardInfoRepoImpl) :
    CardInfoInteractor {
    override suspend fun getCardInfo(token: String, id: String): ShowPanDataModel {
        val cardInfoResponse = cardInfoRepo.getCardInfo(token, id)
        return ShowPanDataModel(
            cardInfoResponse?.data?.number,
            cardInfoResponse?.data?.cvx2,
            cardInfoResponse?.data?.expMonth,
            cardInfoResponse?.data?.expYear,
        )
    }
}