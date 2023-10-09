package com.example.multimoduleapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.domain.TextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val interactor: TextUseCase) : ViewModel() {

    fun getText() = interactor.getText()

}