package com.example.multimoduleapp

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.databinding.FragmentLocationBinding


class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.inflateMenu(R.menu.main_menu)
        val menu = binding?.toolbar?.menu
        val menuItem = menu?.findItem(R.id.action_settings)
        menuItem?.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_settingsFragment)
            return@setOnMenuItemClickListener false
        }
        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }

        binding?.countries?.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_NEXT) {
                v.findNavController().navigate(R.id.action_locationFragment_to_cardPaletteFragment)
            }
            false
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
            it.findNavController().navigate(R.id.action_locationFragment_to_cardPaletteFragment)
        }
    }
}