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
import com.google.android.material.button.MaterialButton
import com.wajahatkarim3.easyflipview.EasyFlipView
import net.pst.cash.R
import net.pst.cash.presentation.model.CardModel
import net.pst.cash.presentation.model.dpToPx

class CardsAdapter(private val context: Context, private val cardModels: List<CardModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.card_item_layout, parent, false)
            return CardViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(context).inflate(R.layout.issue_card_item_layout, parent, false)
            return IssueCardViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val cardViewHolder = holder as CardViewHolder
            setGradient(cardViewHolder)
            cardViewHolder.cardBalance.text = cardModels[position].balance
            cardViewHolder.cardHolderFront.text = cardModels[position].holderName
            cardViewHolder.cardHolderBack.text = cardModels[position].holderName
            cardViewHolder.cardNumLastDigits.text = cardModels[position].lastCardDigits
            cardViewHolder.cardExpiryDate.text = cardModels[position].expireDate
            cardViewHolder.cardInfoFront.setOnClickListener {
                cardViewHolder.easyFlipView.setFlipTypeFromFront()
                cardViewHolder.easyFlipView.flipTheView()
            }
            cardViewHolder.clickedArea.setOnClickListener {
                cardViewHolder.easyFlipView.setFlipTypeFromBack()
                cardViewHolder.easyFlipView.flipTheView()
            }
        } else {
            val issueCardViewHolder = holder as IssueCardViewHolder
        }
    }

    override fun getItemCount(): Int {
        return cardModels.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardInfoFront: CardView
        val cardInfoBack: CardView
        val cardBalance: TextView
        val cardHolderFront: TextView
        val cardHolderBack: TextView
        val clickedArea: View
        val cardNumLastDigits: TextView
        val cardExpiryDate: TextView
        val easyFlipView: EasyFlipView


        init {
            cardInfoFront = itemView.findViewById(R.id.cardInfoFront)
            cardInfoBack = itemView.findViewById(R.id.cardInfoBack)
            cardBalance = itemView.findViewById(R.id.cardBalance)
            cardHolderFront = itemView.findViewById(R.id.cardHolderFront)
            cardHolderBack = itemView.findViewById(R.id.cardHolderBack)
            clickedArea = itemView.findViewById(R.id.clickedArea)
            cardNumLastDigits = itemView.findViewById(R.id.cardNumLastDigits)
            cardExpiryDate = itemView.findViewById(R.id.expDate)
            easyFlipView = itemView.findViewById(R.id.easyFlipView)
        }
    }

    inner class IssueCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val issueCardButton: MaterialButton

        init {
            issueCardButton = itemView.findViewById(R.id.issueCard)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (cardModels[position].id != null) {
            0
        } else {
            1
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
            holder.cardInfoFront.background = layerDrawable
            holder.cardInfoBack.background = layerDrawable
        }
    }
}