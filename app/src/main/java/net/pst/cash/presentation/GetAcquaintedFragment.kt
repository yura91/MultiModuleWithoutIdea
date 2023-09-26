package net.pst.cash.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import net.pst.cash.R
import net.pst.cash.databinding.FragmentGetAcquaintedBinding
import net.pst.cash.presentation.model.formatDate
import net.pst.cash.presentation.model.hideKeyBoard
import java.util.Calendar


class GetAcquaintedFragment :
    BaseFragment<FragmentGetAcquaintedBinding>(FragmentGetAcquaintedBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
        val calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val dateSetListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val formattedDate = formatDate(calendar)
                binding?.birthDate?.text = formattedDate
            }

        val datePickerDialog =
            DatePickerDialog(requireContext(), dateSetListener, year, month, dayOfMonth);

        binding?.birthDate?.setOnClickListener {
            datePickerDialog.show()
        }

        binding?.actionMore?.setOnClickListener {
            findNavController().navigate(R.id.action_getAcquaintedFragment_to_settings_nav_graph)
        }

        binding?.lastNameField?.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_NEXT) {
                requireActivity().hideKeyBoard(view)
                datePickerDialog.show()
            }
            false
        }

        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_getAcquaintedFragment_to_locationFragment)
        }
    }
}




