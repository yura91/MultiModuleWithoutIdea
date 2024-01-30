package net.pst.cash.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.databinding.FragmentWebViewBinding
import net.pst.cash.presentation.viewmodels.AppleWebViewViewModel

@AndroidEntryPoint
class AppleWebViewFragment : BaseFragment<FragmentWebViewBinding>(FragmentWebViewBinding::inflate) {
    private val viewModel: AppleWebViewViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.snackBarErrorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show();
        }

        viewModel.navigateToGetAquintedScreen.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_webViewFragment_to_getAcquaintedFragment)
        }

        viewModel.navigateToReadyScreen.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_appleWebViewFragment_to_cardListFragment)
        }

        viewModel.configData.observe(viewLifecycleOwner) {
            viewModel.registerHash = it?.registerHash
        }

        viewModel.navigateToCardPaletteScreen.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_appleWebViewFragment_to_cardPaletteFragment)
        }

        val args: AppleWebViewFragmentArgs by navArgs()
        val loadUrl = args.link
        binding?.webView?.settings?.javaScriptEnabled = true
        binding?.webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                Log.d("INTERURL", url)
                if (url.startsWith("https://vue3.pstage.net")) {
                    val uri = Uri.parse(url)
                    val code = uri.getQueryParameter("code")
                    viewModel.sendAppleCodeToBackend(code?.trim())
                }
                return true
            }
        }
        binding?.webView?.loadUrl(loadUrl)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding?.webView?.canGoBack() == true) {
                binding?.webView?.goBack()
            } else {
                findNavController().popBackStack()
            }
        }
    }
}
