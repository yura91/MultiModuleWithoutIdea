package net.pst.cash.presentation

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.CardsAdapter.CardViewHolder

class CardsAdapter(private val context: Context) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item_layout, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.tvName.text = String.format("Row number  %d ", position)
        if (position % 2 == 0) {
            holder.imgBanner.setBackgroundColor(Color.RED)
        } else {
            holder.imgBanner.setBackgroundColor(Color.GREEN)
        }
    }

    override fun getItemCount(): Int {
        return 15
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var imgBanner: ImageView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            imgBanner = itemView.findViewById(R.id.imgBanner)
        }
    }
}