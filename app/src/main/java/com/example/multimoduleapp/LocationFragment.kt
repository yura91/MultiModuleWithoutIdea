package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentLocationBinding


class LocationFragment : Fragment() {
    private lateinit var binding: FragmentLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val counriesList = listOf("Russia", "Iran", "Turkey", "Andorra")
        val customAdapter = CustomAdapter(counriesList)
        binding.countries.adapter = customAdapter;
        binding.next.setOnClickListener {
            it.findNavController().navigate(R.id.action_locationFragment_to_cardPaletteFragment)
        }
    }
}