package net.pst.cash.data.repos

import net.pst.cash.data.responses.IssueCardResponse

interface IssueCardRepo {
    suspend fun issueCard(
        token: String,
        accountId: Int,
        cardBalance: String
    ): IssueCardResponse?
}