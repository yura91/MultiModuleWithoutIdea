package com.example.multimoduleapp.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.databinding.FragmentLegalInformationBinding


class LegalInformationFragment :
    BaseFragment<FragmentLegalInformationBinding>(FragmentLegalInformationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}
