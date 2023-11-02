package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.data.paging.TransactionModel


class HistoryPaymentsAdapter :
    PagingDataAdapter<TransactionModel, HistoryPaymentsAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val historyItems: RecyclerView

        init {
            textView = view.findViewById(R.id.title)
            historyItems = view.findViewById(R.id.historyItems)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<TransactionModel>() {
            override fun areItemsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean {
                return false
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.history_payment_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currItem = getItem(position)
        val datePart = currItem?.datePart
        viewHolder.textView.text = datePart
        /*viewHolder.historyItems.adapter =
            HistoryPaymentsItemAdapter(dataSet[position].elements)*/
    }
}
