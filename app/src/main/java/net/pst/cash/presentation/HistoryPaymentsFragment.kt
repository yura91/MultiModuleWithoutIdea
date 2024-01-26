package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.pst.cash.databinding.FragmentHistoryPaymentsBinding
import net.pst.cash.presentation.viewmodels.HistoryPaymentsViewModel

@AndroidEntryPoint
class HistoryPaymentsFragment : BaseDialogFragment<FragmentHistoryPaymentsBinding>(
    FragmentHistoryPaymentsBinding::inflate
) {
    private val historyViewModel: HistoryPaymentsViewModel by viewModels()
    private val historyAdapter = HistoryPaymentsAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = arguments
        val cardId = args?.getInt(cardIdTag)
        binding?.historyPayments?.adapter = historyAdapter
        binding?.swipeContainer?.setOnRefreshListener {
            lifecycleScope.launch {
                historyViewModel.getTransactionHistory(cardId.toString()).collect {
                    binding?.swipeContainer?.isRefreshing = false
                    historyAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            historyViewModel.getTransactionHistory(cardId.toString()).collect {
                historyAdapter.submitData(it)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

        binding?.toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        private const val cardIdTag = "cardId"
    }
}

