package com.example.multimoduleapp.ui.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var endColor: MutableLiveData<Color> = MutableLiveData()
    var startColor: MutableLiveData<Color> = MutableLiveData()
}