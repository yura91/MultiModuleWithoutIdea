package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
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

        cardListViewModel.cardList.observe(viewLifecycleOwner) {
            val cardsAdapter = CardsAdapter(requireContext(), it) {
                val navOptions =
                    NavOptions.Builder()
                        .setEnterAnim(R.anim.slide_in_top)
                        .setExitAnim(R.anim.slide_out_top)
                        .setPopEnterAnim(R.anim.slide_in_bottom)
                        .setPopExitAnim(R.anim.slide_out_bottom)
                        .build()
                findNavController().navigate(
                    R.id.action_cardListFragment_to_selectBalanceFragment,
                    null,
                    navOptions
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

    companion object {
        private const val argsTag = "showAdditionalItems"
    }
}