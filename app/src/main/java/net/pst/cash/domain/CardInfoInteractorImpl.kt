package net.pst.cash.domain

import net.pst.cash.data.repos.CardInfoRepo
import net.pst.cash.domain.model.ShowPanDataModel
import javax.inject.Inject

class CardInfoInteractorImpl @Inject constructor(private val cardInfoRepo: CardInfoRepo) :
    CardInfoInteractor {
    override suspend fun getCardInfo(token: String, id: String): ShowPanDataModel {
        val cardInfoResponse = cardInfoRepo.getCardInfo(token, id)
        val lastCardDigits = cardInfoResponse?.data?.number?.let { it.substring(it.length - 4) }
        return ShowPanDataModel(
            lastCardDigits,
            cardInfoResponse?.data?.number,
            cardInfoResponse?.data?.cvx2,
            cardInfoResponse?.data?.expMonth,
            cardInfoResponse?.data?.expYear,
        )
    }
}