package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.data.responses.IssueCardResponse
import net.pst.cash.domain.model.ErrorModel

interface IssueCardInteractor {
    val errorModel: LiveData<ErrorModel>
    suspend fun issueCard(
        token: String,
        accountId: Int,
        cardBalance: String
    ): IssueCardResponse?
}