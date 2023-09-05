package com.example.multimoduleapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.multimoduleapp.databinding.FragmentGetAcquaintedBinding
import java.util.Calendar

class GetAcquaintedFragment :
    BaseFragment<FragmentGetAcquaintedBinding>(FragmentGetAcquaintedBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val dateSetListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate: String = dayOfMonth.toString() + "." + (month + 1) + "." + year
                binding?.birthDate?.text = selectedDate
            }
        val datePickerDialog =
            DatePickerDialog(requireContext(), dateSetListener, year, month, dayOfMonth);

        binding?.birthDate?.setOnClickListener {
            datePickerDialog.show()
        }
        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_getAcquaintedFragment_to_locationFragment)
        }
    }
}

