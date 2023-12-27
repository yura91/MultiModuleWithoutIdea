package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.model.CardModel

class CardListAdapter(private val dataSet: List<CardModel>) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardBalanceText: TextView

        init {
            cardBalanceText = view.findViewById(R.id.cardBalance)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_item_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val balanceAmount = dataSet[position].balance
        val currencySign = dataSet[position].currencyType
        holder.cardBalanceText.text =
            holder.itemView.context.getString(R.string.balance, balanceAmount, currencySign)
    }


    override fun getItemCount() = dataSet.size
}