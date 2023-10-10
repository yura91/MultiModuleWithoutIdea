package net.pst.cash.data.responses


class CountriesListResponse {
    var data: List<Country>? = null
}

data class Country(
    val id: Int = 0,
    val title: String? = null,
    val iso2: String? = null,
    val iso3: String? = null,
    val block: Boolean = false
)