package net.pst.cash.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class CardPaletteViewModel @Inject constructor(private val application: Application) :
    AndroidViewModel(application) {
    var colorProgress: Int? = null
    var startColor: Int = -1
    var endColor: Int = -1
}