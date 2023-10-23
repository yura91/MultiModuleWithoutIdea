package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.databinding.FragmentHistoryPaymentsBinding
import net.pst.cash.presentation.model.HistoryItem
import net.pst.cash.presentation.model.HistoryPaymentModel
import net.pst.cash.presentation.viewmodels.TransactionsListViewModel

@AndroidEntryPoint
class HistoryPaymentsFragment : BaseFragment<FragmentHistoryPaymentsBinding>(
    FragmentHistoryPaymentsBinding::inflate
) {
    private val transactionViewModel: TransactionsListViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transactionViewModel
        val historyItems1: MutableList<HistoryItem> = mutableListOf()
        val historyPayments: MutableList<HistoryPaymentModel> = mutableListOf()
        historyItems1.add(HistoryItem("Top up", "+ 100,00 €", "23:59"))
        historyItems1.add(HistoryItem("Netflix", "- 8,57 €", "23:55"))
        historyItems1.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyItems1.add(HistoryItem("Amazon", "- 12,99 €", "1:01"))
        historyItems1.add(HistoryItem("PS Store", "+ 100,00 €", "23:59"))
        historyPayments.add(HistoryPaymentModel("Today", historyItems1))
        val historyItems2: MutableList<HistoryItem> = mutableListOf()
        historyItems2.add(HistoryItem("Top up", "+ 100,00 €", "23:59"))
        historyItems2.add(HistoryItem("Netflix", "- 8,57 €", "23:55"))
        historyItems2.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyItems2.add(HistoryItem("Amazon", "- 12,99 €", "1:01"))
        historyPayments.add(HistoryPaymentModel("Yesterday", historyItems2))

        val historyItems3: MutableList<HistoryItem> = mutableListOf()
        historyItems3.add(HistoryItem("Zepliin", "- 8,57 €", "18:55"))
        historyItems3.add(HistoryItem("Miro", "- 12,99 €", "9:55"))
        historyItems3.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyItems3.add(HistoryItem("Amazon", "- 12,99 €", "1:01"))
        historyPayments.add(HistoryPaymentModel("10 May", historyItems3))

        val historyItems4: MutableList<HistoryItem> = mutableListOf()
        historyItems4.add(HistoryItem("Zepliin", "- 8,57 €", "18:55"))
        historyItems4.add(HistoryItem("Miro", "- 12,99 €", "9:55"))
        historyItems4.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyItems4.add(HistoryItem("Amazon", "- 12,99 €", "1:01"))
        historyPayments.add(HistoryPaymentModel("8 May", historyItems3))

        val historyItems5: MutableList<HistoryItem> = mutableListOf()
        historyItems5.add(HistoryItem("Zepliin", "- 8,57 €", "18:55"))
        historyItems5.add(HistoryItem("Miro", "- 12,99 €", "9:55"))
        historyPayments.add(HistoryPaymentModel("5 May", historyItems5))

        val historyItems6: MutableList<HistoryItem> = mutableListOf()
        historyItems6.add(HistoryItem("Zepliin", "- 8,57 €", "18:55"))
        historyItems6.add(HistoryItem("Miro", "- 12,99 €", "9:55"))
        historyItems6.add(HistoryItem("Apple TV+", "- 12,99 €", "6:15"))
        historyPayments.add(HistoryPaymentModel("3 May", historyItems6))

        binding?.historyPayments?.adapter = HistoryPaymentsAdapter(historyPayments)

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}