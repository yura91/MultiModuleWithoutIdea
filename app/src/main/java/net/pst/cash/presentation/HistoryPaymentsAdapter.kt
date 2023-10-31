package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.model.RowHistoryItems


class HistoryPaymentsAdapter(
    private var dataSet: List<RowHistoryItems>,
    private var onLoadMoreListener: OnLoadMoreListener
) : RecyclerView.Adapter<HistoryPaymentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val historyItems: RecyclerView

        init {
            textView = view.findViewById(R.id.title)
            historyItems = view.findViewById(R.id.historyItems)
        }
    }

    fun updateEmployeeListItems(dataSet: List<RowHistoryItems>) {
        val diffCallback = RowHistoryDiffCallback(this.dataSet, dataSet)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.dataSet = dataSet
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.history_payment_item, viewGroup, false)

        return ViewHolder(view)
    }

    fun addHistoryItems(dataSet: List<RowHistoryItems>) {
        val mutableList = this.dataSet.toMutableList()
        mutableList.addAll(dataSet)
        val oldSize = this.dataSet.size
        val newSize = dataSet.size
        this.dataSet = mutableList

        notifyItemRangeInserted(oldSize, newSize)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val datePart = dataSet[position].date
        viewHolder.textView.text = datePart
        viewHolder.historyItems.adapter =
            HistoryPaymentsItemAdapter(dataSet[position].elements)
        if (position >= dataSet.size - 1) {
            onLoadMoreListener.onLoadMore()
        }
    }

    override fun getItemCount() = dataSet.size
}

class RowHistoryDiffCallback(
    rowHistoryItems: List<RowHistoryItems>,
    newRowHistoryItems: List<RowHistoryItems>
) :
    DiffUtil.Callback() {
    private val mRowHistoryItems: List<RowHistoryItems>
    private val mNewRowHistoryItems: List<RowHistoryItems>

    init {
        mRowHistoryItems = rowHistoryItems
        mNewRowHistoryItems = newRowHistoryItems
    }

    override fun getOldListSize(): Int {
        return mRowHistoryItems.size
    }

    override fun getNewListSize(): Int {
        return mNewRowHistoryItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val rowHistoryItemsOld: RowHistoryItems = mRowHistoryItems[oldItemPosition]
        val rowHistoryItemsNew: RowHistoryItems = mNewRowHistoryItems[newItemPosition]
        return rowHistoryItemsOld == rowHistoryItemsNew
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
