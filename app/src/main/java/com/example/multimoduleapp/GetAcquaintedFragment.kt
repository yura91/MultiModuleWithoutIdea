package com.example.multimoduleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentGetAcquaintedBinding

class GetAcquaintedFragment : Fragment() {
    private lateinit var binding: FragmentGetAcquaintedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetAcquaintedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            it.findNavController().navigate(R.id.action_getAcquaintedFragment_to_locationFragment)
        }
    }
}