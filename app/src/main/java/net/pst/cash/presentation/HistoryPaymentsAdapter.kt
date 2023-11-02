package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.model.RowHistoryItems


class HistoryPaymentsAdapter :
    PagingDataAdapter<RowHistoryItems, HistoryPaymentsAdapter.ViewHolder>(diffCallback) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tittle: TextView
        val historyItems: RecyclerView

        init {
            tittle = view.findViewById(R.id.title)
            historyItems = view.findViewById(R.id.historyItems)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<RowHistoryItems>() {
            override fun areItemsTheSame(
                oldItem: RowHistoryItems,
                newItem: RowHistoryItems
            ): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(
                oldItem: RowHistoryItems,
                newItem: RowHistoryItems
            ): Boolean {
                return oldItem.elements == newItem.elements
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
        val datePart = currItem?.date
        viewHolder.tittle.text = datePart

        viewHolder.historyItems.adapter =
            currItem?.elements?.let { HistoryPaymentsItemAdapter(it) }
    }
}
