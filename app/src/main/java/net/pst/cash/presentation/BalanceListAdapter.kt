package net.pst.cash.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.model.BalanceItemModel


class BalanceListAdapter(
    private val userBalance: String,
    private val dataSet: List<BalanceItemModel>,
    private val enoughMoneyAction: () -> Unit,
    private val notEnoughMoneyAction: () -> Unit
) :
    RecyclerView.Adapter<BalanceListAdapter.ViewHolder>() {
    private var selectedPos = RecyclerView.NO_POSITION
    private val USER_BALANCE = 60.00

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
        if (selectedPos == holder.layoutPosition) {
            holder.amountBalanceTextView.setTextColor(holder.itemView.context.getColor(R.color.blue))
            holder.usdtTextView.setTextColor(holder.itemView.context.getColor(R.color.blue))
        } else {
            holder.amountBalanceTextView.setTextColor(holder.itemView.context.getColor(R.color.black))
            holder.usdtTextView.setTextColor(holder.itemView.context.getColor(R.color.black))
        }
        holder.amountBalanceTextView.text = dataSet[holder.layoutPosition].balanceAmount
        holder.usdtTextView.text = dataSet[holder.layoutPosition].usdt
        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPos);
            selectedPos = holder.layoutPosition;
            notifyItemChanged(selectedPos);
            val cost = dataSet[holder.layoutPosition].usdt
            val costParts = cost.split(" ")
            if ((userBalance.toDouble() - costParts[0].toDouble()) < 0) {
                notEnoughMoneyAction()
            } else {
                enoughMoneyAction()
            }
        }
    }

    override fun getItemCount() = dataSet.size
}