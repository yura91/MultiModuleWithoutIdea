package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.databinding.FragmentHistoryPaymentsBinding
import net.pst.cash.presentation.viewmodels.HistoryPaymentsViewModel

@AndroidEntryPoint
class HistoryPaymentsFragment : BaseFragment<FragmentHistoryPaymentsBinding>(
    FragmentHistoryPaymentsBinding::inflate
), OnLoadMoreListener {
    private val historyViewModel: HistoryPaymentsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        historyViewModel.transList.observe(viewLifecycleOwner) {
            binding?.historyPayments?.adapter = HistoryPaymentsAdapter(it, this)
        }

        historyViewModel.transMoreList.observe(viewLifecycleOwner) {
        }

        historyViewModel.getTransactionHistory()

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onLoadMore() {
        historyViewModel.getMoreTransactions()
    }
}


interface OnLoadMoreListener {
    fun onLoadMore()
}
