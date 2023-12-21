package net.pst.cash.domain

import net.pst.cash.data.responses.IssueCardResponse

interface IssueCardInteractor {
    suspend fun issueCard(
        token: String,
        accountId: String?,
        startBalance: String?
    ): IssueCardResponse?
}