package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.domain.model.TransactionModel


class HistoryPaymentsAdapter(private val dataSet: Map<String, List<TransactionModel>>) :
    RecyclerView.Adapter<HistoryPaymentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val historyItems: RecyclerView

        init {
            textView = view.findViewById(R.id.title)
            historyItems = view.findViewById(R.id.historyItems)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.history_payment_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val datePart = dataSet.keys.toList()[position]
        viewHolder.textView.text = datePart
        viewHolder.historyItems.adapter = dataSet[datePart]?.let { HistoryPaymentsItemAdapter(it) }
    }

    override fun getItemCount() = dataSet.size
}