package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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
                historyViewModel.getPaymentsHistory(cardId.toString()).collect {
                    binding?.swipeContainer?.isRefreshing = false
                    historyAdapter.submitData(it)
                }
            }
        }
        historyAdapter.addLoadStateListener { loadState ->
            val error = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }

            error?.let {
                Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
            }
        }

        lifecycleScope.launch {
            historyViewModel.getPaymentsHistory(cardId.toString()).collect {
                historyAdapter.submitData(it)
            }
        }

        binding?.toolbar?.setNavigationOnClickListener {
            dismiss()
        }
    }

    companion object {
        private const val cardIdTag = "cardId"
    }
}

