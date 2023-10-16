package com.example.multimoduleapp.ui.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardPaletteViewModel : ViewModel() {
    val selectedColor: MutableLiveData<Color> = MutableLiveData()
    val startColor: MutableLiveData<Color> = MutableLiveData()
    private val _progressValue = MutableLiveData(0)
    val progressValue: LiveData<Int> get() = _progressValue

    fun setColors(startColor: Color, selectedColor: Color) {
        this.startColor.value = startColor
        this.selectedColor.value = selectedColor
    }

    fun updateCount(newCount: Int) {
        _progressValue.value = newCount
    }
}