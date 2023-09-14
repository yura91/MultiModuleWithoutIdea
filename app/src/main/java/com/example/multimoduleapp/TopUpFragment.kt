package com.example.multimoduleapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.multimoduleapp.databinding.FragmentTopUpBinding

class TopUpFragment : BaseFragment<FragmentTopUpBinding>(FragmentTopUpBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.copyQr?.setOnClickListener {
            Toast.makeText(requireContext(), "QR is copied", Toast.LENGTH_SHORT).show()
        }
        binding?.share?.setOnClickListener {
            Toast.makeText(requireContext(), "Share is clicked", Toast.LENGTH_SHORT).show()
        }

        binding?.support?.setOnClickListener {
            Toast.makeText(requireContext(), "Support is clicked", Toast.LENGTH_SHORT).show()
        }
    }
}