package net.pst.cash.presentation

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import net.pst.cash.R
import net.pst.cash.presentation.CardsAdapter.CardViewHolder
import net.pst.cash.presentation.model.dpToPx

class CardsAdapter(private val context: Context) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item_layout, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.tvName.text = String.format("Row number  %d ", position)
        setGradient(holder)
    }

    override fun getItemCount(): Int {
        return 15
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var cardInfo: CardView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            cardInfo = itemView.findViewById(R.id.cardInfo)
        }
    }

    private fun setGradient(holder: CardViewHolder) {
        val sharedPref =
            holder.itemView.context.getSharedPreferences(
                holder.itemView.context.getString(R.string.myprefs),
                Context.MODE_PRIVATE
            )
        val userId = sharedPref.getString(holder.itemView.context.getString(R.string.userid), "")
        val startColor = sharedPref.getInt(
            userId + holder.itemView.context.getString(R.string.startcolor),
            IssueCardFragment.defColorValue
        )
        val endColor = sharedPref.getInt(
            userId + holder.itemView.context.getString(R.string.endcolor),
            IssueCardFragment.defColorValue
        )

        if (startColor != IssueCardFragment.defColorValue && endColor != IssueCardFragment.defColorValue) {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(startColor, endColor)
            )
            val outValue = TypedValue()
            holder.itemView.context.resources.getValue(R.dimen.corner_radius, outValue, true)
            val cornerRadius = outValue.float
            gradientDrawable.cornerRadius = holder.itemView.context.dpToPx(cornerRadius)
            val layer1 = gradientDrawable
            val layer2 =
                AppCompatResources.getDrawable(
                    holder.itemView.context,
                    R.drawable.card_background_bg
                )
            val layers = arrayOf(layer1, layer2)
            val layerDrawable = LayerDrawable(layers)
            holder.cardInfo.background = layerDrawable
        }
    }
}