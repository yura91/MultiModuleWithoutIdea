package com.example.multimoduleapp

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.databinding.FragmentLocationBinding
import com.example.multimoduleapp.model.hideKeyBoard


class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
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
                v.findNavController().navigate(R.id.action_locationFragment_to_design_nav_graph)
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


        val counriesList = listOf(
            "Russia",
            "Iran",
            "Turkey",
            "Andorra",
            "Germany",
            "Holand",
            "France",
            "China",
            "Japan",
            "USA"
        )
        val countriesAdapter = CountriesAdapter(requireContext(), counriesList)
        binding?.countries?.setAdapter(countriesAdapter)

        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_locationFragment_to_design_nav_graph)
        }
    }
}