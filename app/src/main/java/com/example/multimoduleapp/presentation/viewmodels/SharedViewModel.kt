package com.example.multimoduleapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.multimoduleapp.presentation.model.GradientModel

class SharedViewModel : ViewModel() {
    private val _gradientData = MutableLiveData<GradientModel?>()
    val gradientData: LiveData<GradientModel?> get() = _gradientData

    fun setGradientData(gradientData: GradientModel?) {
        _gradientData.value = gradientData
    }
}
