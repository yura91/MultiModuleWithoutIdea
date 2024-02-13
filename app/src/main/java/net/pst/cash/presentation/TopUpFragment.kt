package net.pst.cash.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentTopUpBinding
import net.pst.cash.presentation.viewmodels.TopUpViewModel


@AndroidEntryPoint
class TopUpFragment : BaseDialogFragment<FragmentTopUpBinding>(FragmentTopUpBinding::inflate) {
    private val topUpViewModel: TopUpViewModel by viewModels()
    private val balanceKey = "balance"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topUpViewModel.account.observe(viewLifecycleOwner) {
            val address = it?.address
            binding?.copyQr?.text = address.toString()
            binding?.copyQr?.isVisible = true
            binding?.share?.isVisible = true
            binding?.toolbar?.cardBalance?.text = it?.balance
            address?.let { address ->
                topUpViewModel.generateQrCodeInBackground(address)
            }
        }

        topUpViewModel.qrCodeLiveData.observe(viewLifecycleOwner) {
            binding?.qrCode?.setImageBitmap(it)
        }

        binding?.toolbar?.addBalance?.isVisible = false


        binding?.toolbar?.actionMore?.setImageResource(R.drawable.ic_close)
        binding?.toolbar?.actionMore?.setOnClickListener {
            dismiss()
        }
        binding?.copyQr?.setOnClickListener {
            Toast.makeText(requireContext(), "QR is copied", Toast.LENGTH_SHORT).show()
            topUpViewModel.copyToClipBoard(binding?.copyQr?.text.toString())
        }
        binding?.share?.setOnClickListener {
            Toast.makeText(requireContext(), "Share is clicked", Toast.LENGTH_SHORT).show()
            val shareBody = binding?.copyQr?.text.toString()
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Address")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            requireContext().startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        binding?.support?.setOnClickListener {
            Toast.makeText(requireContext(), "Support is clicked", Toast.LENGTH_SHORT).show()
        }
    }
}