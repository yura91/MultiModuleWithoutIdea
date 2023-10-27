package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.model.HistoryItem


class HistoryPaymentsAdapter(
    private var dataSet: List<Map<String, List<HistoryItem>>>,
    private var onLoadMoreListener: OnLoadMoreListener
) :
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

    fun addHistoryItems(dataSet: Map<String, List<HistoryItem>>) {
        val mutableList = this.dataSet.toMutableList()
        mutableList.add(dataSet)
        val oldSize = this.dataSet.size
        val newSize = dataSet.size
        this.dataSet = mutableList

        notifyItemRangeInserted(oldSize, newSize)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val historyMap: Map<String, List<HistoryItem>> = dataSet[position]
        val datePart = historyMap.keys.toList()[0]
        viewHolder.textView.text = datePart
        viewHolder.historyItems.adapter =
            historyMap[datePart]?.let { HistoryPaymentsItemAdapter(it) }
        if (position >= dataSet.size - 1) {
            onLoadMoreListener.onLoadMore()
        }
    }

    override fun getItemCount() = dataSet.size
}
