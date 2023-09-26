package net.pst.cash.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.pst.cash.presentation.model.GradientModel

class SharedViewModel : ViewModel() {
    private val _gradientData = MutableLiveData<GradientModel?>()
    val gradientData: LiveData<GradientModel?> get() = _gradientData

    fun setGradientData(gradientData: GradientModel?) {
        _gradientData.value = gradientData
    }
}
