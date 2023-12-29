package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentLoadingBinding
import net.pst.cash.presentation.viewmodels.LoadingViewModel

@AndroidEntryPoint
class LoadingFragment : BaseFragment<FragmentLoadingBinding>(FragmentLoadingBinding::inflate) {
    private val viewModel by viewModels<LoadingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateToLoginScreen.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loadingFragment_to_signInFragment)
        }
        viewModel.navigateToReadyScreen.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loadingFragment_to_cardListFragment)
        }

        viewModel.navigateToGetAcquaintedScreen.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loadingFragment_to_getAcquaintedFragment)
        }

        viewModel.navigateToCardPaletteScreen.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loadingFragment_to_cardPaletteFragment)
        }
    }
}