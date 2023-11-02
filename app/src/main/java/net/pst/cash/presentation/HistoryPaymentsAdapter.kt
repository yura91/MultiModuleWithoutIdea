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
        val tittle: TextView
        val description: TextView
        val operationValue: TextView
        val operationTime: TextView

        init {
            tittle = view.findViewById(R.id.title)
            description = view.findViewById(R.id.operationType)
            operationValue = view.findViewById(R.id.operationValue)
            operationTime = view.findViewById(R.id.operationTime)
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
        viewHolder.tittle.text = datePart

        viewHolder.description.text = currItem?.description
        viewHolder.operationValue.text = currItem?.sum
        viewHolder.operationTime.text = currItem?.timePart
        /*viewHolder.historyItems.adapter =
            HistoryPaymentsItemAdapter(dataSet[position].elements)*/
    }
}
