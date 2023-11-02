package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.pst.cash.databinding.FragmentHistoryPaymentsBinding
import net.pst.cash.presentation.viewmodels.HistoryPaymentsViewModel

@AndroidEntryPoint
class HistoryPaymentsFragment : BaseFragment<FragmentHistoryPaymentsBinding>(
    FragmentHistoryPaymentsBinding::inflate
) {
    private val historyViewModel: HistoryPaymentsViewModel by viewModels()
    private val historyAdapter = HistoryPaymentsAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.historyPayments?.adapter = historyAdapter

        lifecycleScope.launch {
            historyViewModel.getTransactionHistory().collect {
                historyAdapter.submitData(it)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}

