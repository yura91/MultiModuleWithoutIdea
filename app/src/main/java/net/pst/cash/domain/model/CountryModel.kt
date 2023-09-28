package net.pst.cash.domain.model

data class CountryModel(
    val id: Int = 0,
    val title: String
) {

    override fun toString(): String {
        return title
    }
}

