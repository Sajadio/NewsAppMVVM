package com.example.newsappmvvm.ui.fragment.article

import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentWebViewBinding
import com.example.newsappmvvm.ui.base.BaseFragment
import com.example.newsappmvvm.ui.viewmodel.NewsViewModel

@ExperimentalPagingApi
class WebViewFragment : BaseFragment<FragmentWebViewBinding>(R.layout.fragment_web_view) {

    private val args: WebViewFragmentArgs by navArgs()

    override fun launchView() {
     binding.url = args.article.url
    }

}