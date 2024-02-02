package net.pst.cash.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class CardPaletteViewModel @Inject constructor(private val application: Application) :
    AndroidViewModel(application) {
    var colorProgress: Int? = null
    var startColor: Int = -1
    var endColor: Int = -1

    fun getExpiryDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MM/yy", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        return formattedDate
    }
}