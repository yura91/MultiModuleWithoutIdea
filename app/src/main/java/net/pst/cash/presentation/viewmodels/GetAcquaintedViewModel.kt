package net.pst.cash.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GetAcquaintedViewModel : ViewModel() {

    val errorFirstname: LiveData<Boolean>
        get() = _errorFirstname

    private val _errorFirstname: MutableLiveData<Boolean> = MutableLiveData()

    val errorLastName: LiveData<Boolean>
        get() = _errorLastname

    private val _errorLastname: MutableLiveData<Boolean> = MutableLiveData()


    fun validateFirstName(text: String) {
        _errorFirstname.value = text.length < 3
    }

    fun validateLastName(text: String) {
        _errorLastname.value = text.length < 3
    }
}