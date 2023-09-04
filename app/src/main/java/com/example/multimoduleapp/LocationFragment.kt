package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentLocationBinding


class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val counriesList = listOf("Russia", "Iran", "Turkey", "Andorra")
        val customAdapter = CustomAdapter(counriesList)
        binding?.countries?.adapter = customAdapter;
        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_locationFragment_to_cardPaletteFragment)
        }
    }
}