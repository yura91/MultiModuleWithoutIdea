package com.example.multimoduleapp.ui.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardPaletteViewModel : ViewModel() {

    var selectedColor: MutableLiveData<Color> = MutableLiveData()
    var startColor: MutableLiveData<Color> = MutableLiveData()

    fun setColors(startColor: Color, selectedColor: Color) {

    }
}