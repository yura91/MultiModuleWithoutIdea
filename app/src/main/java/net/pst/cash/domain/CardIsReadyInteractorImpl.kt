package net.pst.cash.domain

import net.pst.cash.data.repos.CardIsReadyRepo
import net.pst.cash.data.responses.CardResponseData
import net.pst.cash.domain.model.CardModel
import javax.inject.Inject

class CardIsReadyInteractorImpl @Inject constructor(private val cardIsReadyRepo: CardIsReadyRepo) :
    CardIsReadyInteractor {
    override suspend fun getActiveCardModel(token: String): CardModel? {
        val cardIsReadyResponse: List<CardResponseData>? = cardIsReadyRepo.checkActiveCard(token)
        return if (!cardIsReadyResponse.isNullOrEmpty()) {
            CardModel(cardIsReadyResponse[0].id, cardIsReadyResponse[0].account?.balance)
        } else {
            null
        }
    }
}