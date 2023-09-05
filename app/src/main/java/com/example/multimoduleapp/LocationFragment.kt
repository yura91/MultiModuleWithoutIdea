package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentLocationBinding


class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val counriesList = mutableListOf<String>(
            "Russia",
            "Iran",
            "Turkey",
            "Andorra",
            "Holand",
            "China",
            "Japan",
            "USA"
        )
        val customAdapter = CustomAdapter(requireContext(), counriesList)
//       val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, counriesList)
        binding?.countries?.setAdapter(customAdapter)
//      binding?.countries?.setOnItemClickListener { parent, view, position, id -> Log.d("FFFF", "mvsmvl;") }

        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_locationFragment_to_cardPaletteFragment)
        }
    }
}