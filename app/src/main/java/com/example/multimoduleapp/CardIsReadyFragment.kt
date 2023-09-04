package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentCardIsReadyBinding

class CardIsReadyFragment : Fragment() {
    private lateinit var binding: FragmentCardIsReadyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardIsReadyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            it.findNavController().navigate(R.id.action_cardIsReadyFragment_to_completeAuthFragment)
        }
    }
}