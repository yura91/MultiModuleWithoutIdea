package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.model.HistoryItem


class HistoryPaymentsItemAdapter(private val dataSet: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryPaymentsItemAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val operationTypeTextView: TextView
        val operationValueTextView: TextView
        val operationTimeTextView: TextView

        init {
            operationTypeTextView = view.findViewById(R.id.operationType)
            operationValueTextView = view.findViewById(R.id.operationValue)
            operationTimeTextView = view.findViewById(R.id.operationTime)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.inner_history_payment_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.operationTypeTextView.text = dataSet[position].description
        if (dataSet[position].status == STATUS) {
            val redColor = ContextCompat.getColor(
                viewHolder.operationTypeTextView.context,
                R.color.text_red_color
            )
            viewHolder.operationValueTextView.setTextColor(redColor)
        } else {
            val blackColor =
                ContextCompat.getColor(viewHolder.operationTypeTextView.context, R.color.text_black)
            viewHolder.operationValueTextView.setTextColor(blackColor)
        }
        viewHolder.operationValueTextView.text = dataSet[position].sum
        viewHolder.operationTimeTextView.text = dataSet[position].timePart
    }

    override fun getItemCount() = dataSet.size

    companion object {
        private const val STATUS = 35
    }
}