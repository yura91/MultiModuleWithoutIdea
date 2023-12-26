package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.model.BalanceItemModel


class BalanceListAdapter(
    private val dataSet: List<BalanceItemModel>,
    private var selectedPos: Int = 0,
    private val clickAction: (String, Double, Int) -> Unit
) : RecyclerView.Adapter<BalanceListAdapter.ViewHolder>() {


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
            .inflate(R.layout.item_balance, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardBalanceAmount = dataSet[holder.layoutPosition].balanceAmount
        holder.amountBalanceTextView.text = cardBalanceAmount
        holder.usdtTextView.text = dataSet[holder.layoutPosition].usdt
        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPos);
            selectedPos = holder.layoutPosition;
            notifyItemChanged(selectedPos);
            val cardCost = dataSet[holder.layoutPosition].usdt.split(" ")[0].toDouble()
            clickAction(dataSet[holder.layoutPosition].balanceAmount, cardCost, selectedPos)
        }
        holder.itemView.setSelected(position == selectedPos)
    }

    override fun getItemCount() = dataSet.size
}