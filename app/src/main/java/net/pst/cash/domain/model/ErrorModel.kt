package net.pst.cash.domain.model

data class ErrorModel(val success: Boolean, val message: String, val type: String? = null)
