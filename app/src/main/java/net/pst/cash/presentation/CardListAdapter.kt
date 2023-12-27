package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R

class CardListAdapter() : RecyclerView.Adapter<CardListAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        /*val amountBalanceTextView: TextView
        val usdtTextView: TextView*/

        init {
            /*amountBalanceTextView = view.findViewById(R.id.balanceAmount)
            usdtTextView = view.findViewById(R.id.usdt)*/
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_item_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    override fun getItemCount() = 10
}