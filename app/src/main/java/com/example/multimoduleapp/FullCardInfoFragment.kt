package com.example.multimoduleapp


import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentFullCardInfoBinding


class FullCardInfoFragment :
    BaseFragment<FragmentFullCardInfoBinding>(FragmentFullCardInfoBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.back?.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}