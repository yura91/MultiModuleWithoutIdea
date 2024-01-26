package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import net.pst.cash.databinding.FragmentLegalInformationBinding


class LegalInformationFragment :
    BaseDialogFragment<FragmentLegalInformationBinding>(FragmentLegalInformationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}
