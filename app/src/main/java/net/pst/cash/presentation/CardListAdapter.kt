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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import net.pst.cash.R
import net.pst.cash.presentation.model.CardModel
import net.pst.cash.presentation.model.dpToPx

class CardListAdapter(private val dataSet: List<CardModel>) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardBalanceText: TextView
        val cardInfo: ShapeableImageView

        init {
            cardBalanceText = view.findViewById(R.id.cardBalance)
            cardInfo = view.findViewById(R.id.cardInfo)
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
        setGradient(holder)
        holder.cardBalanceText.text =
            holder.itemView.context.getString(R.string.balance, balanceAmount, currencySign)
    }


    override fun getItemCount() = dataSet.size

    private fun setGradient(holder: ViewHolder) {
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