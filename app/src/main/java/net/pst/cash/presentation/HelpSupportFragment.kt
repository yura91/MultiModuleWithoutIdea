package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import net.pst.cash.databinding.FragmentHelpSupportBinding


class HelpSupportFragment :
    BaseDialogFragment<FragmentHelpSupportBinding>(FragmentHelpSupportBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbar?.setNavigationOnClickListener {
           dismiss()
        }
    }
}
