package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.model.RowHistoryItems


class HistoryPaymentsAdapter(
    private var onLoadMoreListener: OnLoadMoreListener
) : ListAdapter<RowHistoryItems, HistoryPaymentsAdapter.ViewHolder>(RowHistoryDiffCallBack()) {

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
        val datePart = getItem(position).date
        viewHolder.textView.text = datePart
        viewHolder.historyItems.adapter =
            HistoryPaymentsItemAdapter(getItem(position).elements)
        if (position >= itemCount - 1) {
            onLoadMoreListener.onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        val count = super.getItemCount()
        return count
        /*return when (count) {
            0 -> 1
            else -> count
        }*/
    }
}

private class RowHistoryDiffCallBack : DiffUtil.ItemCallback<RowHistoryItems>() {
    override fun areItemsTheSame(oldItem: RowHistoryItems, newItem: RowHistoryItems): Boolean =
        false

    override fun areContentsTheSame(oldItem: RowHistoryItems, newItem: RowHistoryItems): Boolean =
        oldItem == newItem
}
