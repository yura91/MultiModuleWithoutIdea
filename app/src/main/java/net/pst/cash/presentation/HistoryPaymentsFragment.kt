package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.databinding.FragmentHistoryPaymentsBinding
import net.pst.cash.presentation.viewmodels.HistoryPaymentsViewModel

@AndroidEntryPoint
class HistoryPaymentsFragment : BaseFragment<FragmentHistoryPaymentsBinding>(
    FragmentHistoryPaymentsBinding::inflate
), OnLoadMoreListener {
    private val historyViewModel: HistoryPaymentsViewModel by viewModels()
    private val historyAdapter = HistoryPaymentsAdapter(mutableListOf(), this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.historyPayments?.adapter = historyAdapter

        val layoutManager = binding?.historyPayments?.layoutManager as LinearLayoutManager

        historyViewModel.transList.observe(viewLifecycleOwner) {
            val position = layoutManager.findFirstVisibleItemPosition()
            historyAdapter.updateEmployeeListItems(it)
            layoutManager.scrollToPosition(position)
        }

        historyViewModel.transMoreList.observe(viewLifecycleOwner) {
            val position = layoutManager.findFirstVisibleItemPosition()
            historyAdapter.updateEmployeeListItems(it)
            layoutManager.scrollToPosition(position)
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
