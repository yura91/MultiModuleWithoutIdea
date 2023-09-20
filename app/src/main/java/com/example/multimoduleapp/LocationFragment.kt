package com.example.multimoduleapp

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.databinding.FragmentLocationBinding


class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
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
            hideKeyBoard(binding?.countries)
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


    private fun hideKeyBoard(
        view: View?
    ) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}