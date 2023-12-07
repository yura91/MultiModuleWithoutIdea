package net.pst.cash.domain

import net.pst.cash.data.repos.ActiveCardsRepo
import net.pst.cash.data.responses.CardResponseData
import net.pst.cash.domain.model.CardModel
import javax.inject.Inject

class ActiveCardInteractorImpl @Inject constructor(private val activeCardsRepo: ActiveCardsRepo) :
    ActiveCardInteractor {
    override suspend fun getActiveCardModel(token: String): CardModel? {
        val activeCardsResponse: List<CardResponseData>? = activeCardsRepo.checkActiveCard(token)
        return if (!activeCardsResponse.isNullOrEmpty()) {
            CardModel(
                activeCardsResponse[0].id,
                activeCardsResponse[0].account?.currencyId,
                activeCardsResponse[0].account?.balance
            )
        } else {
            null
        }
    }
}