package com.example.newsappmvvm.presentation.ui.fragment

import androidx.navigation.fragment.navArgs
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentWebViewBinding
import com.example.newsappmvvm.presentation.ui.NewsActivity
import com.example.newsappmvvm.presentation.ui.base.BaseFragment

class WebViewFragment : BaseFragment<FragmentWebViewBinding>(R.layout.fragment_web_view) {

    private val args: WebViewFragmentArgs by navArgs()

    override fun initial() {
        (activity as NewsActivity).supportActionBar?.title = args.article.source?.name
        binding.lifecycleOwner = viewLifecycleOwner
        binding.webView.apply {
            loadUrl(args.article.url.toString())
        }
    }

}