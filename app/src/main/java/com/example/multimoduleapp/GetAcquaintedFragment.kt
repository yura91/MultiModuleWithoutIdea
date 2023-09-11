package com.example.multimoduleapp

import android.app.DatePickerDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.multimoduleapp.databinding.FragmentGetAcquaintedBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class GetAcquaintedFragment :
    BaseFragment<FragmentGetAcquaintedBinding>(FragmentGetAcquaintedBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.inflateMenu(R.menu.main_menu)

        val menu = binding?.toolbar?.menu
        val menuItem = menu?.findItem(R.id.action_settings)
        menuItem?.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_getAcquaintedFragment_to_settingsFragment)
            return@setOnMenuItemClickListener false
        }
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

        binding?.lastNameField?.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_NEXT) {
                hideKeyBoard(view, datePickerDialog)
            }
            false
        }

        binding?.next?.setOnClickListener {
            it.findNavController().navigate(R.id.action_getAcquaintedFragment_to_locationFragment)
        }
    }

    private fun hideKeyBoard(
        view: View,
        datePickerDialog: DatePickerDialog
    ) {
        val imm =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        datePickerDialog.show()
    }

    private fun formatDate(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}




