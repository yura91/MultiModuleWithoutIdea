package com.example.multimoduleapp

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
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
    }
}
