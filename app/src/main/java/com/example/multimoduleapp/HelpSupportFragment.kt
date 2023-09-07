package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentHelpSupportBinding


class HelpSupportFragment :
    BaseFragment<FragmentHelpSupportBinding>(FragmentHelpSupportBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}
