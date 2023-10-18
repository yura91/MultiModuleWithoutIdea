package net.pst.cash.presentation

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentLocationBinding
import net.pst.cash.domain.model.CountryModel
import net.pst.cash.presentation.model.hideKeyBoard
import net.pst.cash.presentation.viewmodels.LocationViewModel

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate) {
    private val viewModel: LocationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
        val args: LocationFragmentArgs by navArgs()
        viewModel.firstName = args.firstName
        viewModel.lastName = args.lastName
        viewModel.birthDate = args.birthDate
        viewModel.countriesList.observe(viewLifecycleOwner) {
            val countriesAdapter = CountriesAdapter(requireContext(), it)
            binding?.countries?.setAdapter(countriesAdapter)

            binding?.countries?.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectedItem = parent?.adapter?.getItem(position) as? CountryModel
                }
            }
        }

        viewModel.snackBarVerificationErrorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show();
        }

        viewModel.snackBarCountriesErrorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show();
        }

        viewModel.banned.observe(viewLifecycleOwner) {
            if (it == true) {
                binding?.location?.isVisible = true
                binding?.next?.isVisible = false
            } else {
                binding?.location?.isVisible = false
                binding?.next?.isVisible = true
            }
        }

        viewModel.verified.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_locationFragment_to_design_nav_graph)
        }

        binding?.countries?.apply {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val height = height

                    dropDownVerticalOffset = -height / 2
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        binding?.countries?.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_NEXT) {
                val sharedPref =
                    requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val token = sharedPref.getString("token", "")
                viewModel.goNext(
                    "Bearer $token"
                )
            }
            false
        }


        binding?.actionMore?.setOnClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_settings_nav_graph)
        }

        binding?.countries?.setOnItemClickListener { parent, view, position, id ->
            requireActivity().hideKeyBoard(binding?.countries)
        }

        binding?.countries?.doAfterTextChanged {
            if (binding?.countries?.isPopupShowing == true) {
                binding?.countries?.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    getDrawable(requireContext(), R.drawable.ic_text_arrow_up),
                    null
                )
            } else {
                binding?.countries?.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    getDrawable(requireContext(), R.drawable.ic_text_arrow_down),
                    null
                )
            }
        }

        binding?.countries?.setOnDismissListener {
            if (binding?.countries?.isPopupShowing == true) {
                binding?.countries?.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    getDrawable(requireContext(), R.drawable.ic_text_arrow_up),
                    null
                )
            } else {
                binding?.countries?.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    getDrawable(requireContext(), R.drawable.ic_text_arrow_down),
                    null
                )
            }
        }

        binding?.next?.setOnClickListener {
            val sharedPref = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            findNavController().navigate(R.id.action_locationFragment_to_design_nav_graph)
            /*token?.let { token ->
                viewModel.goNext(
                    "Bearer $token"
                )
            }*/
        }
    }
}