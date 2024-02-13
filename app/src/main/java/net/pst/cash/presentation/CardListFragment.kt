package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentCardListBinding
import net.pst.cash.presentation.viewmodels.CardListViewModel

@AndroidEntryPoint
class CardListFragment : BaseFragment<FragmentCardListBinding>(FragmentCardListBinding::inflate) {
    private val cardListViewModel: CardListViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbarBinding = binding?.toolbar

        val args = arguments
        val newCardId = args?.getInt("newCardId")
        newCardId?.let {
            cardListViewModel.newCardId = it
        }

        toolbarBinding?.actionMore?.setOnClickListener {

            val currentPosition = binding?.cardCarousel?.currentItem
            val cardsAdapter = binding?.cardCarousel?.adapter as CardsAdapter
            val currentCardModel = currentPosition?.let {
                if (it < cardsAdapter.itemCount) {
                    return@let cardsAdapter.getItemData(it)
                } else {
                    return@let null
                }
            }
            val bundle = Bundle()

            val cardList = cardListViewModel.cardList.value

            if (cardList != null && cardList.size > 1) {
                bundle.putBoolean(showAdItemsTag, true)
            }

            currentCardModel?.id?.let {
                bundle.putInt(cardIdTag, it)
            }

            currentCardModel?.accountId?.let {
                bundle.putInt(accountIdTag, it)
            }
            findNavController().navigate(
                R.id.action_cardListFragment_to_settings_nav_graph,
                bundle
            )
        }

        cardListViewModel.account.observe(viewLifecycleOwner) {
            binding?.toolbar?.cardBalance?.text = it
        }

        cardListViewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }

        cardListViewModel.errorLoadCardList.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
        val cardsAdapter = CardsAdapter(requireContext(), listOf(), {
            findNavController().navigate(
                R.id.action_cardListFragment_to_selectBalanceFragment
            )
        }, { cardid ->
            val bundle = Bundle()
            bundle.putInt(cardIdTag, cardid)

            findNavController().navigate(
                R.id.action_cardListFragment_to_historyPaymentsFragment,
                bundle
            )
        }, {
            cardListViewModel.getCardInfo(it)
        }) {
            cardListViewModel.updateCard(it)
            cardListViewModel.updateCardHistory(it)
        }

        binding?.cardCarousel?.adapter = cardsAdapter
        cardListViewModel.cardList.observe(viewLifecycleOwner) {
            cardsAdapter.updateCardModels(it)
            cardListViewModel.getAllCardHistories()
        }
        cardListViewModel.deleteCardPos.observe(viewLifecycleOwner) {
            cardsAdapter.removeCardModel(it)
        }
        cardListViewModel.cardInfoModelPos.observe(viewLifecycleOwner) {
            cardsAdapter.updateCardModel(it)
        }

        cardListViewModel.cardHistoriesList.observe(viewLifecycleOwner) {
            cardsAdapter.updateCardModels(it)
        }

        binding?.toolbar?.addBalance?.setOnClickListener {
            findNavController().navigate(
                R.id.action_cardListFragment_to_topUpFragment
            )
        }

        binding?.cardCarousel?.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
            val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
            val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
            setPageTransformer { page, position ->
                val myOffset = position * -(2 * pageOffset + pageMargin)
                if (binding?.cardCarousel?.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -myOffset
                    } else {
                        page.translationX = myOffset
                    }
                } else {
                    page.translationY = myOffset
                }
            }
        }

        cardListViewModel.getActiveBalance()
        cardListViewModel.getAllCards()
    }

    companion object {
        private const val showAdItemsTag = "showAdditionalItems"
        private const val cardIdTag = "cardId"
        private const val accountIdTag = "accountId"
    }
}