package net.pst.cash.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import net.pst.cash.R
import net.pst.cash.databinding.FragmentGetAcquaintedBinding
import net.pst.cash.presentation.model.formatDate
import net.pst.cash.presentation.model.hideKeyBoard
import net.pst.cash.presentation.viewmodels.GetAcquaintedViewModel
import java.util.Calendar


class GetAcquaintedFragment :
    BaseFragment<FragmentGetAcquaintedBinding>(FragmentGetAcquaintedBinding::inflate) {
    private val getAcquaintedViewModel by viewModels<GetAcquaintedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
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
                getAcquaintedViewModel.setSelectedDate(year, month, dayOfMonth)
                val formattedDate = formatDate(calendar)
                getAcquaintedViewModel.formattedDate = formattedDate
                binding?.birthDate?.text = formattedDate
            }

        val datePickerDialog =
            DatePickerDialog(requireContext(), dateSetListener, year, month, dayOfMonth);

        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }

        binding?.birthDate?.setOnClickListener {
            datePickerDialog.show()
        }
        getAcquaintedViewModel.formattedDate?.let {
            binding?.birthDate?.text = getAcquaintedViewModel.formattedDate
        }

        binding?.actionMore?.setOnClickListener {
            findNavController().navigate(R.id.action_getAcquaintedFragment_to_settings_nav_graph)
        }

        getAcquaintedViewModel.errorFirstname.observe(viewLifecycleOwner) {
            if (it) {
                binding?.firstNameField?.error = getString(R.string.error_input_text)
            } else {
                binding?.firstNameField?.error = null
            }
        }

        getAcquaintedViewModel.errorLastName.observe(viewLifecycleOwner) {
            if (it) {
                binding?.lastNameField?.error = getString(R.string.error_input_text)
            } else {
                binding?.lastNameField?.error = null
            }
        }

        getAcquaintedViewModel.canGoNext.observe(viewLifecycleOwner) {
            if (it) {
                val action =
                    GetAcquaintedFragmentDirections.actionGetAcquaintedFragmentToLocationFragment(
                        binding?.firstNameField?.text.toString(),
                        binding?.lastNameField?.text.toString(),
                        binding?.birthDate?.text.toString()
                    )
                findNavController().navigate(action)
            }
        }

        binding?.firstNameField?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                val result = s.toString().replace(" ", "")
                if (s.toString() != result) {
                    binding?.firstNameField?.setText(result)
                    binding?.firstNameField?.setSelection(result.length)
                }
            }
        })

        binding?.lastNameField?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                val result = s.toString().replace(" ", "")
                if (s.toString() != result) {
                    binding?.lastNameField?.setText(result)
                    binding?.lastNameField?.setSelection(result.length)
                }
            }
        })

        binding?.firstNameField?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding?.firstNameField?.text.toString()
                getAcquaintedViewModel.validateFirstName(text)
            }
        }

        binding?.lastNameField?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding?.lastNameField?.text.toString()
                getAcquaintedViewModel.validateLastName(text)
            }
        }


        binding?.lastNameField?.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_NEXT) {
                requireActivity().hideKeyBoard(view)
                datePickerDialog.show()
            }
            false
        }

        binding?.next?.setOnClickListener {
            val action =
                GetAcquaintedFragmentDirections.actionGetAcquaintedFragmentToLocationFragment(
                    binding?.firstNameField?.text.toString(),
                    binding?.lastNameField?.text.toString(),
                    binding?.birthDate?.text.toString()
                )
            findNavController().navigate(action)
        }
    }
}




