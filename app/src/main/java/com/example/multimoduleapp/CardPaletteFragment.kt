package com.example.multimoduleapp

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.multimoduleapp.databinding.FragmentCardPaletteBinding

class CardPaletteFragment : Fragment() {
    private lateinit var binding: FragmentCardPaletteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardPaletteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.colorSeekBar.setOnColorChangeListener { progress, color ->
            binding.cardDesign.setColorFilter(
                color,
                PorterDuff.Mode.MULTIPLY
            )
        }
    }
}