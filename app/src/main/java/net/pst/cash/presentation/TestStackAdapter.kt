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
import com.google.android.material.imageview.ShapeableImageView
import net.pst.cash.R
import net.pst.cash.presentation.cardstack.CardStackView
import net.pst.cash.presentation.cardstack.StackAdapter
import net.pst.cash.presentation.model.CardModel
import net.pst.cash.presentation.model.dpToPx


class TestStackAdapter(context: Context?) : StackAdapter<CardModel?>(context) {
    override fun bindView(data: CardModel?, position: Int, holder: CardStackView.ViewHolder) {
        if (holder is ViewHolder) {
            holder.onBind(data, position)
        }
    }

    override fun onCreateView(parent: ViewGroup, viewType: Int): CardStackView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item_layout, parent, false)
        return ViewHolder(view)
    }

    internal class ViewHolder(view: View) : CardStackView.ViewHolder(view) {
        val cardBalanceText: TextView
        val cardInfo: ShapeableImageView

        init {
            cardBalanceText = view.findViewById(R.id.cardBalance)
            cardInfo = view.findViewById(R.id.cardInfo)
        }

        override fun onItemExpand(b: Boolean) {

        }

        override fun onAnimationStateChange(state: Int, willBeSelect: Boolean) {
            super.onAnimationStateChange(state, willBeSelect)
            if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
                onItemExpand(true)
            }
            if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
                onItemExpand(false)
            }
        }

        fun onBind(data: CardModel?, position: Int) {
            val balanceAmount = data?.balance
            val currencySign = data?.currencyType
            setGradient()
            cardBalanceText.text =
                itemView.context.getString(R.string.balance, balanceAmount, currencySign)
        }

        private fun setGradient() {
            val sharedPref =
                itemView.context.getSharedPreferences(
                    itemView.context.getString(R.string.myprefs),
                    Context.MODE_PRIVATE
                )
            val userId = sharedPref.getString(itemView.context.getString(R.string.userid), "")
            val startColor = sharedPref.getInt(
                userId + itemView.context.getString(R.string.startcolor),
                IssueCardFragment.defColorValue
            )
            val endColor = sharedPref.getInt(
                userId + itemView.context.getString(R.string.endcolor),
                IssueCardFragment.defColorValue
            )

            if (startColor != IssueCardFragment.defColorValue && endColor != IssueCardFragment.defColorValue) {
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(startColor, endColor)
                )
                val outValue = TypedValue()
                itemView.context.resources.getValue(R.dimen.corner_radius, outValue, true)
                val cornerRadius = outValue.float
                gradientDrawable.cornerRadius = itemView.context.dpToPx(cornerRadius)
                val layer1 = gradientDrawable
                val layer2 =
                    AppCompatResources.getDrawable(
                        itemView.context,
                        R.drawable.card_background_bg
                    )
                val layers = arrayOf(layer1, layer2)
                val layerDrawable = LayerDrawable(layers)
                cardInfo.background = layerDrawable
            }
        }
    }
}