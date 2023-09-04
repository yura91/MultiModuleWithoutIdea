package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.multimoduleapp.databinding.FragmentCompleteAuthBinding


class CompleteAuthFragment : Fragment() {
    private lateinit var binding: FragmentCompleteAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompleteAuthBinding.inflate(inflater, container, false)
        return binding.root
    }
}