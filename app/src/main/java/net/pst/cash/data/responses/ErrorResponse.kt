package net.pst.cash.data.responses

data class ErrorResponse(val success: Boolean, val message: String, val type: String? = null)