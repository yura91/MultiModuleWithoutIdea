package net.pst.cash.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        val testStackAdapter = TestStackAdapter(requireContext())
        binding?.stackView?.setAdapter(testStackAdapter)
        binding?.stackView?.setItemExpendListener {

        }
        toolbarBinding?.actionMore?.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(argsTag, true)
            findNavController().navigate(
                R.id.action_cardListFragment_to_settings_nav_graph,
                bundle
            )
        }
//        binding?.cardList?.addItemDecoration(VerticalSpaceItemDecoration(8f.dpToPx().toInt()))

        cardListViewModel.account.observe(viewLifecycleOwner) {
            binding?.toolbar?.cardBalance?.text = it
        }

        cardListViewModel.cardList.observe(viewLifecycleOwner) {
//            binding?.cardList?.adapter = CardListAdapter(it)
            testStackAdapter.updateData(it)
        }

        cardListViewModel.getActiveBalance()
        cardListViewModel.getAllCards()
    }

    companion object {
        private const val argsTag = "showAdditionalItems"
    }
}