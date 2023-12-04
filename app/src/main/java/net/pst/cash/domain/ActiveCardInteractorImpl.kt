package net.pst.cash.domain

import net.pst.cash.data.repos.ActiveCardsRepo
import net.pst.cash.data.responses.CardResponseData
import net.pst.cash.domain.model.CardModel
import javax.inject.Inject

class ActiveCardInteractorImpl @Inject constructor(private val activeCardsRepo: ActiveCardsRepo) :
    ActiveCardInteractor {
    override suspend fun getActiveCardModel(token: String): CardModel? {
        val cardIsReadyResponse: List<CardResponseData> = activeCardsRepo.checkActiveCard(token)
        return if (!cardIsReadyResponse.isNullOrEmpty()) {
            CardModel(
                cardIsReadyResponse[0].id,
                cardIsReadyResponse[0].account?.currencyId,
                cardIsReadyResponse[0].account?.balance
            )
        } else {
            null
        }
    }
}