package net.pst.cash.presentation

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.wajahatkarim3.easyflipview.EasyFlipView
import net.pst.cash.R
import net.pst.cash.presentation.model.CardModel
import net.pst.cash.presentation.model.copyToClipboard
import net.pst.cash.presentation.model.dpToPx

class CardsAdapter(
    private val context: Context,
    private var cardModels: MutableList<CardModel>,
    val issueCardAction: () -> Unit,
    val showPaymentsAction: (cardId: Int) -> Unit,
    val getCardInfoAction: (cardId: Int?) -> Unit,
    val updateCardAction: (cardId: Int?) -> Unit,
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

    fun updateCardModels(cardModels: List<CardModel>) {
        this.cardModels.clear()
        this.cardModels.addAll(cardModels)
        notifyItemRangeChanged(0, this.cardModels.size)
    }

    fun updateCardModel(cardModelPos: Int) {
        notifyItemChanged(cardModelPos)
    }

    fun removeCardModel(cardModelPos: Int) {
        notifyItemRemoved(cardModelPos)
    }

    fun getItemData(position: Int): CardModel {
        return cardModels[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == cardType) {
            val cardViewHolder = holder as CardViewHolder
            cardViewHolder.setGradient()
            val balance = cardModels[position].balance
            val currency = cardModels[position].currencyType
            cardViewHolder.cardBalance.text =
                context.getString(R.string.card_balance, balance, currency)
            cardViewHolder.cardInfoFront.setOnClickListener {
                cardViewHolder.easyFlipView.setFlipTypeFromFront()
                cardViewHolder.easyFlipView.flipTheView()
            }
            cardViewHolder.clickedArea.setOnClickListener {
                cardViewHolder.easyFlipView.setFlipTypeFromBack()
                cardViewHolder.easyFlipView.flipTheView()
            }

            cardViewHolder.copyCardNumber.setOnClickListener {
                context.copyToClipboard(cardViewHolder.fullCardNumber.text)
                Toast.makeText(
                    context,
                    context.getString(R.string.card_number_is_copied), Toast.LENGTH_SHORT
                ).show()
            }

            cardViewHolder.copyExpDate.setOnClickListener {
                context.copyToClipboard(cardViewHolder.cardExpiryDateBack.text)
                Toast.makeText(
                    context,
                    context.getString(R.string.expiry_date_is_copied), Toast.LENGTH_SHORT
                ).show()
            }

            cardViewHolder.copyCvv.setOnClickListener {
                context.copyToClipboard(cardViewHolder.cvv.text)
                Toast.makeText(
                    context,
                    context.getString(R.string.cvv_is_copied), Toast.LENGTH_SHORT
                ).show()
            }

            cardViewHolder.easyFlipView.setOnFlipListener { easyFlipView, newCurrentSide ->
                if (newCurrentSide == EasyFlipView.FlipState.BACK_SIDE) {
                    getCardInfoAction(cardModels[position].id)
                }
            }

            val lastCardDigits = cardModels[position].lastCardDigits
            if (lastCardDigits != null) {
                cardViewHolder.cardNumLastDigits.text = context.getString(
                    R.string.short_card_number,
                    cardModels[position].lastCardDigits
                )
            } else {
                cardViewHolder.cardNumLastDigits.text = ""
            }

            val fullCardNumber = cardModels[position].fullCardNumber
            if (fullCardNumber != null) {
                cardViewHolder.backSideCardNumberShimmer.isVisible = false
                cardViewHolder.fullCardNumberLayout.isVisible = true
                cardViewHolder.fullCardNumber.text = cardModels[position].fullCardNumber
            } else {
                cardViewHolder.backSideCardNumberShimmer.isVisible = true
                cardViewHolder.fullCardNumberLayout.isVisible = false
            }

            val cvv = cardModels[position].cvv
            if (cvv != null) {
                cardViewHolder.backSideCvvShimmer.isVisible = false
                cardViewHolder.cvvLayout.isVisible = true
                cardViewHolder.cvv.text = cardModels[position].cvv
            } else {
                cardViewHolder.backSideCvvShimmer.isVisible = true
                cardViewHolder.cvvLayout.isVisible = false
            }

            val expDate = cardModels[position].expireDate
            if (expDate != null) {
                cardViewHolder.shimmerExpDateBack.isVisible = false
                cardViewHolder.expDateBackLayout.isVisible = true
                cardViewHolder.cardExpiryDateBack.text = expDate
                cardViewHolder.cardExpiryDateFront.text = expDate
            } else {
                cardViewHolder.shimmerExpDateBack.isVisible = true
                cardViewHolder.expDateBackLayout.isVisible = false
            }

            cardViewHolder.swipeContainer.setOnRefreshListener {
                updateCardAction(cardModels[position].id)
                cardViewHolder.swipeContainer.isRefreshing = false
            }
            cardViewHolder.cardItemLayout.setTransitionListener(object :
                MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {

                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    if (currentId == R.id.collapsed) {
                        cardViewHolder.swipeContainer.isEnabled = false
                    } else if (currentId == R.id.expanded) {
                        cardViewHolder.swipeContainer.isEnabled = true
                    }
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                }
            })

            val historyItems = cardModels[position].rowHistoryItems
            val expandedSet = cardViewHolder.cardItemLayout.getConstraintSet(R.id.expanded)
            val collapsedSet = cardViewHolder.cardItemLayout.getConstraintSet(R.id.collapsed)
            if (historyItems.isEmpty()) {
                expandedSet.setVisibility(R.id.shortHistoryPaymentList, ConstraintSet.INVISIBLE)
                collapsedSet.setVisibility(R.id.shortHistoryPaymentList, ConstraintSet.INVISIBLE)
                expandedSet.setVisibility(R.id.paymentsLayout, ConstraintSet.INVISIBLE)
                collapsedSet.setVisibility(R.id.paymentsLayout, ConstraintSet.INVISIBLE)
                expandedSet.setVisibility(R.id.shimmer1, ConstraintSet.VISIBLE)
                collapsedSet.setVisibility(R.id.shimmer1, ConstraintSet.VISIBLE)
                expandedSet.setVisibility(R.id.shimmer2, ConstraintSet.VISIBLE)
                collapsedSet.setVisibility(R.id.shimmer2, ConstraintSet.VISIBLE)
                expandedSet.setVisibility(R.id.shimmer3, ConstraintSet.VISIBLE)
                collapsedSet.setVisibility(R.id.shimmer3, ConstraintSet.VISIBLE)
                cardViewHolder.cardItemLayout.getTransition(R.id.transition).isEnabled = false
            } else {
                expandedSet.setVisibility(R.id.shimmer1, ConstraintSet.GONE)
                collapsedSet.setVisibility(R.id.shimmer1, ConstraintSet.GONE)
                expandedSet.setVisibility(R.id.shimmer2, ConstraintSet.GONE)
                collapsedSet.setVisibility(R.id.shimmer2, ConstraintSet.GONE)
                expandedSet.setVisibility(R.id.shimmer3, ConstraintSet.GONE)
                collapsedSet.setVisibility(R.id.shimmer3, ConstraintSet.GONE)
                expandedSet.setVisibility(R.id.shortHistoryPaymentList, ConstraintSet.VISIBLE)
                collapsedSet.setVisibility(R.id.shortHistoryPaymentList, ConstraintSet.VISIBLE)
                expandedSet.setVisibility(R.id.paymentsLayout, ConstraintSet.VISIBLE)
                collapsedSet.setVisibility(R.id.paymentsLayout, ConstraintSet.VISIBLE)
                cardViewHolder.cardItemLayout.getTransition(R.id.transition).isEnabled = true
            }
                expandedSet.setVisibility(R.id.shortHistoryPaymentList, ConstraintSet.VISIBLE)
                collapsedSet.setVisibility(R.id.shortHistoryPaymentList, ConstraintSet.VISIBLE)
                expandedSet.setVisibility(R.id.paymentsLayout, ConstraintSet.VISIBLE)
                collapsedSet.setVisibility(R.id.paymentsLayout, ConstraintSet.VISIBLE)
                cardViewHolder.cardItemLayout.getTransition(R.id.transition).isEnabled = true
                var index = 0
                historyItems.forEach {
                    if (index < cardViewHolder.historyDates.size && it.date.isNotEmpty()) {
                        cardViewHolder.historyDates[index].isVisible = true
                        cardViewHolder.historyDates[index].text = it.date
                    } else if (index < cardViewHolder.historyDates.size) {
                        cardViewHolder.historyDates[index].isVisible = false
                        cardViewHolder.historyDates[index].text = it.date
                    }
                    it.elements.forEach { historyItem ->
                        val historyLayout = cardViewHolder.historyItemLayouts[index]
                        val operationType = historyLayout.findViewById<TextView>(R.id.operationType)
                        val operationValue =
                            historyLayout.findViewById<TextView>(R.id.operationValue)
                        val operationTime = historyLayout.findViewById<TextView>(R.id.operationTime)
                        operationType.text = historyItem.description
                        operationValue.text = historyItem.sum
                        operationTime.text = historyItem.timePart
                        index++
                    }
                }

            cardViewHolder.payments.setOnClickListener {
                cardModels[position].id?.let { showPaymentsAction(it) }
            }
            cardViewHolder.viewAllButton.setOnClickListener {
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
        val cardExpiryDateFront: TextView
        val cardExpiryDateBack: TextView
        val easyFlipView: EasyFlipView
        val viewAllButton: MaterialButton
        val swipeContainer: SwipeRefreshLayout
        val shortHistoryPaymentList: LinearLayout
        val fullCardNumberLayout: LinearLayout
        val expDateBackLayout: LinearLayout
        val shortHistoryShimmer1: ShimmerFrameLayout
        val backSideCardNumberShimmer: ShimmerFrameLayout
        val shimmerExpDateBack: ShimmerFrameLayout
        val backSideCvvShimmer: ShimmerFrameLayout
        val historyItemLayout1: ConstraintLayout
        val historyItemLayout2: ConstraintLayout
        val historyItemLayout3: ConstraintLayout
        val historyItemLayouts: MutableList<ConstraintLayout> = mutableListOf()
        val historyDates: MutableList<TextView> = mutableListOf()
        val payments: MaterialButton
        val fullCardNumber: TextView
        val date1: TextView
        val date2: TextView
        val date3: TextView
        val copyCardNumber: ImageView
        val copyExpDate: ImageView
        val copyCvv: ImageView
        val cvv: TextView
        val cvvLayout: LinearLayout
        val cardItemLayout: MotionLayout
        val collapsedSet: ConstraintSet
        val expandedSet: ConstraintSet

        init {
            cardInfoFront = itemView.findViewById(R.id.cardInfoFront)
            cardInfoBack = itemView.findViewById(R.id.cardInfoBack)
            cardBalance = itemView.findViewById(R.id.cardBalance)
            clickedArea = itemView.findViewById(R.id.clickedArea)
            viewAllButton = itemView.findViewById(R.id.viewAllHistButton)
            cardNumLastDigits = itemView.findViewById(R.id.cardNumLastDigits)
            swipeContainer = itemView.findViewById(R.id.swipeContainer)
            copyCardNumber = itemView.findViewById(R.id.copyCardNumber)
            copyExpDate = itemView.findViewById(R.id.copyExpDate)
            copyCvv = itemView.findViewById(R.id.copyCvv)
            cardExpiryDateFront = itemView.findViewById(R.id.expDateFront)
            cardExpiryDateBack = itemView.findViewById(R.id.expDateBack)
            payments = itemView.findViewById(R.id.payments)
            easyFlipView = itemView.findViewById(R.id.easyFlipView)
            fullCardNumber = itemView.findViewById(R.id.cardNumber)
            cvv = itemView.findViewById(R.id.cvv)
            shortHistoryShimmer1 = itemView.findViewById(R.id.shimmer1)
            backSideCardNumberShimmer = itemView.findViewById(R.id.backSideCardNumberShimmer)
            shimmerExpDateBack = itemView.findViewById(R.id.backSideExpDateShimmer)
            backSideCvvShimmer = itemView.findViewById(R.id.backSideCvvShimmer)
            shortHistoryPaymentList = itemView.findViewById(R.id.shortHistoryPaymentList)
            fullCardNumberLayout = itemView.findViewById(R.id.fullCardNumber)
            cvvLayout = itemView.findViewById(R.id.cvvLayout)
            cardItemLayout = itemView.findViewById(R.id.cardItemLayout)
            expDateBackLayout = itemView.findViewById(R.id.expDateBackLayout)
            expandedSet = cardItemLayout.getConstraintSet(R.id.expanded)
            collapsedSet = cardItemLayout.getConstraintSet(R.id.collapsed)
            historyItemLayout1 = itemView.findViewById(R.id.inner_history_payment_item)
            historyItemLayout2 = itemView.findViewById(R.id.inner_history_payment_item2)
            historyItemLayout3 = itemView.findViewById(R.id.inner_history_payment_item3)
            historyItemLayouts.add(historyItemLayout1)
            historyItemLayouts.add(historyItemLayout2)
            historyItemLayouts.add(historyItemLayout3)
            date1 = itemView.findViewById(R.id.date1)
            date2 = itemView.findViewById(R.id.date2)
            date3 = itemView.findViewById(R.id.date3)
            historyDates.add(date1)
            historyDates.add(date2)
            historyDates.add(date3)
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