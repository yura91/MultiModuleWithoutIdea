package net.pst.cash.domain

import androidx.lifecycle.LiveData
import net.pst.cash.data.responses.IssueCardResponse

interface IssueCardInteractor {
    val errorMessage: LiveData<String>
    suspend fun issueCard(
        token: String,
        accountId: Int,
        cardBalance: String
    ): IssueCardResponse?
}