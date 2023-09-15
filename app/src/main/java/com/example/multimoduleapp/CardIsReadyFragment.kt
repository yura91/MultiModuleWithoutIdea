package com.example.multimoduleapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.multimoduleapp.databinding.FragmentCardIsReadyBinding
import com.example.multimoduleapp.viewmodels.SharedViewModel

class CardIsReadyFragment :
    BaseFragment<FragmentCardIsReadyBinding>(FragmentCardIsReadyBinding::inflate) {
    val viewModel: SharedViewModel by navGraphViewModels(R.id.nav_graph)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startColor.observe(viewLifecycleOwner) {
            Log.d("TAG", "it")
        }

        viewModel.endColor.observe(viewLifecycleOwner) {
            Log.d("TAG", "it")
        }
        binding?.toolbar?.inflateMenu(R.menu.main_menu)
        val menu = binding?.toolbar?.menu
        val menuItem = menu?.findItem(R.id.action_settings)
        menuItem?.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_cardIsReadyFragment_to_settingsFragment)
            return@setOnMenuItemClickListener false
        }
        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_cardIsReadyFragment_to_completeAuthFragment)
        }
    }
}