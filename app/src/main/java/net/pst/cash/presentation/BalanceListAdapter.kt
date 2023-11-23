package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.model.BalanceItemModel


class BalanceListAdapter(private val dataSet: List<BalanceItemModel>) :
    RecyclerView.Adapter<BalanceListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val amountBalanceTextView: TextView
        val usdtTextView: TextView


        init {
            amountBalanceTextView = view.findViewById(R.id.balanceAmount)
            usdtTextView = view.findViewById(R.id.usdt)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_balance_list, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.amountBalanceTextView.text = dataSet[position].balanceAmount
        holder.usdtTextView.text = dataSet[position].usdt
    }


    override fun getItemCount() = dataSet.size


}