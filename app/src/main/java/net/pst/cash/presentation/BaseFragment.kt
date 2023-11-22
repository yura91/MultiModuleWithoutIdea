package net.pst.cash.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> T) :
    Fragment() {

    private var _binding: T? = null

    protected val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showDialog(
        titleId: Int,
        positiveId: Int,
        negativeId: Int,
        positiveAction: () -> Unit,
        negativeAction: () -> Unit
    ) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(
            requireContext()
        );

        alertDialogBuilder.setMessage(
            getString(titleId)
        );

        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton(
                getString(positiveId)
            ) { dialog, which ->
                positiveAction()
            }
            .setNegativeButton(
                getString(negativeId)
            ) { dialog, which ->
                negativeAction()
            }
        val alertDialog: AlertDialog = alertDialogBuilder.create();
        alertDialog.show()
    }
}