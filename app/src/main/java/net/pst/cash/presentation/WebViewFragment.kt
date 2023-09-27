package net.pst.cash.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.pst.cash.databinding.FragmentWebViewBinding

class WebViewFragment : BaseFragment<FragmentWebViewBinding>(FragmentWebViewBinding::inflate) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: WebViewFragmentArgs by navArgs()
        val loadUrl = args.link
        binding?.webView?.settings?.javaScriptEnabled = true
        binding?.webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()

                if (url.startsWith("https://vue3.pstage.net")) {
                    val uri = Uri.parse(url)
                    val code = uri.getQueryParameter("code")

                    // Делайте что угодно с кодом

                    return true // Это остановит загрузку URL
                }

                return super.shouldOverrideUrlLoading(view, request)
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
