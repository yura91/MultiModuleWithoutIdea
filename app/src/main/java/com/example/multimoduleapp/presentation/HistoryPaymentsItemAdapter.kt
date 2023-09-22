package com.example.multimoduleapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.multimoduleapp.R
import com.example.multimoduleapp.presentation.model.HistoryItem

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
        viewHolder.operationTypeTextView.text = dataSet[position].operationType
        viewHolder.operationValueTextView.text = dataSet[position].operationValue
        viewHolder.operationTimeTextView.text = dataSet[position].operationTime
    }

    override fun getItemCount() = dataSet.size

}