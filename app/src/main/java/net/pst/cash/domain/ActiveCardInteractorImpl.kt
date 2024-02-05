package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.data.repos.ActiveCardsRepo
import net.pst.cash.data.responses.CardResponseData
import net.pst.cash.domain.model.CardModel
import javax.inject.Inject

class ActiveCardInteractorImpl @Inject constructor(private val activeCardsRepo: ActiveCardsRepo) :
    ActiveCardInteractor {

    override val errorMessage: LiveData<String> = activeCardsRepo.errorMessage
    override suspend fun getAllCards(token: String): List<CardModel>? {
        val activeCardsResponse: List<CardResponseData>? = activeCardsRepo.getAllCards(token)
        return if (!activeCardsResponse.isNullOrEmpty()) {
            val cardModels: MutableList<CardModel> = mutableListOf()
            activeCardsResponse.forEach { cardDataModel ->
                val cardModel = CardModel(
                    cardDataModel.id,
                    cardDataModel.account?.currencyId,
                    cardDataModel.account?.balance,
                    cardDataModel.mask.let { it?.substring(it.length - 4) }
                )
                cardModels.add(cardModel)
            }
            return cardModels
        } else {
            null
        }
    }
}