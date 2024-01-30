package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import net.pst.cash.databinding.FragmentLegalInformationBinding


class LegalInformationFragment :
    BaseDialogFragment<FragmentLegalInformationBinding>(FragmentLegalInformationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbar?.setNavigationOnClickListener {
           dismiss()
        }
    }
}
