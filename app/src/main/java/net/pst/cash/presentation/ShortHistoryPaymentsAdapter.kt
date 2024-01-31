package net.pst.cash.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import net.pst.cash.R
import net.pst.cash.presentation.model.RowHistoryItems


class ShortHistoryPaymentsAdapter(
    private val context: Context,
    val id: Int?,
    private val dataSet: List<RowHistoryItems>,
    val viewAllAction: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class HistoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tittle: TextView
        val historyItems: RecyclerView

        init {
            tittle = view.findViewById(R.id.title)
            historyItems = view.findViewById(R.id.historyItems)
        }
    }

    class FooterHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewAll: MaterialButton

        init {
            viewAll = view.findViewById(R.id.viewAllHistButton)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == itemType) {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.history_payment_item, viewGroup, false)
            HistoryItemViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.view_all_button_item_layout, viewGroup, false)
            FooterHolder(view)
        }
    }


    override fun getItemCount() = dataSet.size

    override fun getItemViewType(position: Int): Int {
        return if (position == dataSet.size - 1) {
            footerType
        } else {
            itemType
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val currItem = dataSet[position]
        val datePart = currItem.date
        if (viewHolder.itemViewType == itemType) {
            viewHolder as HistoryItemViewHolder
            if (datePart != "") {
                viewHolder.tittle.isVisible = true
                viewHolder.tittle.text = datePart
            } else {
                viewHolder.tittle.isVisible = false
                viewHolder.tittle.text = ""
            }

            viewHolder.historyItems.adapter =
                HistoryPaymentsItemAdapter(currItem.elements)
        } else {
            viewHolder as FooterHolder
            viewHolder.viewAll.setOnClickListener {
                id?.let { viewAllAction(it) }
            }
        }
    }

    companion object {
        const val itemType = 0
        const val footerType = 1
    }
}
