package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentCardIsReadyBinding

class CardIsReadyFragment :
    BaseFragment<FragmentCardIsReadyBinding>(FragmentCardIsReadyBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.inflateMenu(R.menu.main_menu)
        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_cardIsReadyFragment_to_completeAuthFragment)
        }
    }
}