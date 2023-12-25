package net.pst.cash.data.repos

import androidx.lifecycle.LiveData
import net.pst.cash.data.responses.ErrorResponse
import net.pst.cash.data.responses.IssueCardResponse

interface IssueCardRepo {
    val errorResponse: LiveData<ErrorResponse>
    val unknownError: LiveData<String>
    suspend fun issueCard(
        token: String,
        accountId: Int,
        cardBalance: String
    ): IssueCardResponse?
}