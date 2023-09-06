package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentCompleteAuthBinding


class CompleteAuthFragment :
    BaseFragment<FragmentCompleteAuthBinding>(FragmentCompleteAuthBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbar?.inflateMenu(R.menu.main_menu)
        binding?.topUpButton?.setOnClickListener {
            it.findNavController().navigate(R.id.action_completeAuthFragment_to_topUpFragment)
        }
    }
}