package net.pst.cash.domain

import net.pst.cash.data.repos.CardIsReadyRepo
import net.pst.cash.data.responses.CardResponseData
import javax.inject.Inject

class CardIsReadyInteractorImpl @Inject constructor(private val cardIsReadyRepo: CardIsReadyRepo) :
    CardIsReadyInteractor {
    override suspend fun getActiveCardId(token: String): Int? {
        val cardIsReadyResponse: List<CardResponseData>? = cardIsReadyRepo.checkActiveCard(token)
        return if (!cardIsReadyResponse.isNullOrEmpty()) {
            cardIsReadyResponse[0].id
        } else {
            null
        }
    }
}