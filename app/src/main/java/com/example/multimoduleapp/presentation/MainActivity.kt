package com.example.multimoduleapp.presentation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.multimoduleapp.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
}