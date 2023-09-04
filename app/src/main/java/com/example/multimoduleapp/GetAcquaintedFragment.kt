package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentGetAcquaintedBinding

class GetAcquaintedFragment :
    BaseFragment<FragmentGetAcquaintedBinding>(FragmentGetAcquaintedBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_getAcquaintedFragment_to_locationFragment)
        }
    }
}