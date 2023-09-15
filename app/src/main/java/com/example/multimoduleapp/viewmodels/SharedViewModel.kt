package com.example.multimoduleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _startColor = MutableLiveData<Int>()
    val startColor: LiveData<Int> get() = _startColor
    private val _endColor = MutableLiveData<Int>()
    val endColor: LiveData<Int> get() = _endColor

    fun setStartColor(startColor: Int) {
        _startColor.value = startColor
    }

    fun setEndColor(endColor: Int) {
        _endColor.value = endColor
    }
}
