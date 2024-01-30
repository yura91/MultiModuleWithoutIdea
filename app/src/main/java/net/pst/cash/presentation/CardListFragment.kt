package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentCardListBinding
import net.pst.cash.presentation.viewmodels.CardListViewModel

@AndroidEntryPoint
class CardListFragment : BaseFragment<FragmentCardListBinding>(FragmentCardListBinding::inflate) {
    private val cardListViewModel: CardListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbarBinding = binding?.toolbar

        toolbarBinding?.actionMore?.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(argsTag, true)
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

        cardListViewModel.cardList.observe(viewLifecycleOwner) {
            val cardsAdapter = CardsAdapter(requireContext(), it, {
                findNavController().navigate(
                    R.id.action_cardListFragment_to_selectBalanceFragment
                )
            }) { cardid ->
                val bundle = Bundle()
                bundle.putInt(cardIdTag, cardid)

                findNavController().navigate(
                    R.id.action_cardListFragment_to_historyPaymentsFragment,
                    bundle
                )
            }
            binding?.cardCarousel?.adapter = cardsAdapter
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

    override fun onPause() {
        super.onPause()
        // Сохраните позицию ViewPager
        cardListViewModel.viewPagerPosition = binding?.cardCarousel?.currentItem!!
    }


    companion object {
        private const val argsTag = "showAdditionalItems"
        private const val cardIdTag = "cardId"
    }
}