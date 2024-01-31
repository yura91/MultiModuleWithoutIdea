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

class CardsAdapter(
    private val context: Context,
    private val cardModels: List<CardModel>,
    val issueCardAction: () -> Unit,
    val showPaymentsAction: (cardId: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == cardType) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.card_item_layout, parent, false)
            CardViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(context).inflate(R.layout.issue_card_item_layout, parent, false)
            IssueCardViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == cardType) {
            val cardViewHolder = holder as CardViewHolder
            cardViewHolder.setGradient()
            cardViewHolder.cardBalance.text =
                context.getString(R.string.card_balance, cardModels[position].balance)
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

            val historyAdapter = ShortHistoryPaymentsAdapter(
                context,
                cardModels[position].id,
                cardModels[position].rowHistoryItems,
                showPaymentsAction
            )

            cardViewHolder.shortHistoryPaymentList.adapter = historyAdapter

            cardViewHolder.payments.setOnClickListener {
                cardModels[position].id?.let { showPaymentsAction(it) }
            }
        } else {
            val issueCardViewHolder = holder as IssueCardViewHolder
            issueCardViewHolder.setGradient()
        }
    }

    override fun getItemCount(): Int {
        return cardModels.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardInfoFront: CardView
        val cardInfoBack: CardView
        val cardBalance: TextView
        val clickedArea: View
        val cardNumLastDigits: TextView
        val cardExpiryDate: TextView
        val easyFlipView: EasyFlipView
        val shortHistoryPaymentList: RecyclerView
        val payments: MaterialButton

        init {
            cardInfoFront = itemView.findViewById(R.id.cardInfoFront)
            cardInfoBack = itemView.findViewById(R.id.cardInfoBack)
            cardBalance = itemView.findViewById(R.id.cardBalance)
            clickedArea = itemView.findViewById(R.id.clickedArea)
            cardNumLastDigits = itemView.findViewById(R.id.cardNumLastDigits)
            cardExpiryDate = itemView.findViewById(R.id.expDate)
            payments = itemView.findViewById(R.id.payments)
            easyFlipView = itemView.findViewById(R.id.easyFlipView)
            shortHistoryPaymentList = itemView.findViewById(R.id.shortHistoryPaymentList)
        }

        fun setGradient() {
            val sharedPref =
                itemView.context.getSharedPreferences(
                    itemView.context.getString(R.string.myprefs),
                    Context.MODE_PRIVATE
                )
            val userId = sharedPref.getString(itemView.context.getString(R.string.userid), "")
            val startColor = sharedPref.getInt(
                userId + itemView.context.getString(R.string.startcolor),
                defColorValue
            )
            val endColor = sharedPref.getInt(
                userId + itemView.context.getString(R.string.endcolor),
                defColorValue
            )

            if (startColor != defColorValue && endColor != defColorValue) {
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
                cardInfoFront.background = layerDrawable
                cardInfoBack.background = layerDrawable
            }
        }
    }

    inner class IssueCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val issueCardButton: MaterialButton
        val issueCardImage: CardView

        init {
            issueCardButton = itemView.findViewById(R.id.issueCard)
            issueCardImage = itemView.findViewById(R.id.cardImage)
            issueCardButton.setOnClickListener {
                issueCardAction()
            }
        }

        fun setGradient() {
            val sharedPref =
                itemView.context.getSharedPreferences(
                    itemView.context.getString(R.string.myprefs),
                    Context.MODE_PRIVATE
                )
            val userId = sharedPref.getString(itemView.context.getString(R.string.userid), "")
            val startColor = sharedPref.getInt(
                userId + itemView.context.getString(R.string.startcolor),
                defColorValue
            )
            val endColor = sharedPref.getInt(
                userId + itemView.context.getString(R.string.endcolor),
                defColorValue
            )

            if (startColor != defColorValue && endColor != defColorValue) {
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
                    AppCompatResources.getDrawable(itemView.context, R.drawable.card_background_bg)
                val layers = arrayOf(layer1, layer2)
                val layerDrawable = LayerDrawable(layers)
                issueCardImage.background = layerDrawable
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (cardModels[position].id != null) {
            cardType
        } else {
            issueType
        }
    }

    companion object {
        const val cardType = 0
        const val issueType = 1
        const val defColorValue = -1
    }
}